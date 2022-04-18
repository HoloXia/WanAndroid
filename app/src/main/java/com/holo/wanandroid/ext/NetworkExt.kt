package com.holo.wanandroid.ext

import com.holo.architecture.base.repository.IRemoteData
import com.holo.architecture.base.repository.IRepository
import com.holo.architecture.logger.KLog
import com.holo.architecture.network.*
import com.holo.architecture.utils.netstatus.NetUtils
import com.holo.wanandroid.network.PageBean
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

/**
 *
 *
 * @Author holo
 * @Date 2022/3/23
 */

fun IRemoteData.buildRequestBody(vararg params: Pair<String, Any?>): RequestBody {
    val reqObj = JSONObject()

    params.forEach { (key, value) ->
        if (value != null) {
            reqObj.put(key, value)
        }
    }

    return reqObj.toString().toRequestBody("application/json;charset=utf-8".toMediaType())
}

/**
 * 处理响应结果，转为State包裹对象
 *
 * @receiver IRemoteData
 * @param block SuspendFunction0<BaseResponse<R>> 请求函数
 * @return State<R>
 */
inline fun <R> IRemoteData.processCall(block: () -> BaseResponse<R>): State<R> {
    val resp = kotlin.runCatching {
        if (!NetUtils.isConnected()) {
            throw AppException(HoloError.NO_NETWORK)
        }
        block()
    }.getOrElse {
        KLog.e(it)
        val appError = ExceptionHandle.handleException(it)
        return State.error(appError)
    }
    return if (resp.isSuccess()) {
        State.success(resp.getResponseData())
    } else {
        State.error(AppException(resp.getResponseMsg(), resp.getResponseCode()))
    }
}

/**
 * 处理响应结果，不关注请求错误
 *
 * @receiver IRemoteData
 * @param block SuspendFunction0<BaseResponse<R>> 请求函数
 * @return State<R>
 */
inline fun <R> IRemoteData.processCallObj(block: () -> BaseResponse<R>): R? {
    val resp = kotlin.runCatching {
        /*if (!NetUtils.isConnected()) {
            throw AppException(HoloError.NO_NETWORK)
        }*/
        block()
    }.getOrElse {
        KLog.e(it)
        return null
    }
    return if (resp.isSuccess()) {
        resp.getResponseData()
    } else {
        null
    }
}

/**
 * 处理分页响应结果，转为ListState包裹对象
 * @receiver IRemoteData
 * @param page Int 请求页码
 * @param pageSize Int 每页条数
 * @param block Function0<BaseResponse<PageBean<R>>> 请求函数
 * @return ListState<R>
 */
inline fun <R> IRemoteData.processListCall(page: Int, block: () -> BaseResponse<PageBean<R>>): ListState<R> {
    val resp = kotlin.runCatching {
        /*if (!NetUtils.isConnected()) {
            return ListState(false, page == 0, err = AppException(HoloError.NO_NETWORK))
        }*/
        block()
    }.getOrElse {
        KLog.e(it)
        val appError = ExceptionHandle.handleException(it)
        return ListState(false, page == 0, err = appError)
    }

    return if (resp.isSuccess()) {
        return resp.getResponseData().run {
            ListState(true, page == 0, !this.over, datas.isEmpty(), datas, total)
        }
    } else {
        ListState(false, page == 0, err = AppException(resp.getResponseMsg(), resp.getResponseCode()))
    }
}

/**
 * 处理分页响应结果，转为ListState包裹对象
 * @receiver IRemoteData
 * @param refresh Int 是否刷新
 * @param block Function0<BaseResponse<PageBean<R>>> 请求函数
 * @return ListState<R>
 */
inline fun <R> IRemoteData.processListCall(refresh: Boolean, block: () -> BaseResponse<PageBean<R>>): ListState<R> {
    val resp = kotlin.runCatching {
        /*if (!NetUtils.isConnected()) {
            return ListState(false, page == 0, err = AppException(HoloError.NO_NETWORK))
        }*/
        block()
    }.getOrElse {
        KLog.e(it)
        val appError = ExceptionHandle.handleException(it)
        return ListState(false, refresh, err = appError)
    }

    return if (resp.isSuccess()) {
        return resp.getResponseData().run {
            ListState(true, refresh, !this.over, datas.isEmpty(), datas, total)
        }
    } else {
        ListState(false, refresh, err = AppException(resp.getResponseMsg(), resp.getResponseCode()))
    }
}

/**
 * Repository直接请求转Flow
 * @receiver IRepository
 * @param dispatcher CoroutineDispatcher
 * @param action SuspendFunction0<State<T>>
 * @return Flow<State<T>>
 */
fun <T> IRepository.toFlow(dispatcher: CoroutineDispatcher, action: suspend () -> State<T>): Flow<State<T>> {
    return flow { emit(action()) }.onStart { emit(State.loading()) }.flowOn(dispatcher)
}
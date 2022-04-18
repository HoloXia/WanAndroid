package com.holo.wanandroid.network

import com.holo.wanandroid.data.dto.*
import retrofit2.http.*

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
interface WanService {

    /**
     * 用户登录
     * @param username String 用户名
     * @param psw String 密码
     * @return ApiResponse<UserInfo>
     */
    @FormUrlEncoded
    @POST(WanApi.API_LOGIN)
    suspend fun login(
        @Field("username") username: String,
        @Field("password") psw: String
    ): ApiResponse<UserInfoBean>

    @GET(WanApi.API_LOGOUT)
    suspend fun logout(): ApiResponse<String?>

    /**
     * 注册
     * @param username String 用户名
     * @param psw String 密码
     * @param rePsw String 重复密码
     * @return ApiResponse<UserInfoBean>
     */
    @FormUrlEncoded
    @POST(WanApi.API_REGISTER)
    suspend fun register(
        @Field("username") username: String,
        @Field("password") psw: String,
        @Field("repassword") rePsw: String
    ): ApiResponse<UserInfoBean>

    /**
     * 获取我的积分
     * @return ApiResponse<IntegralBean>
     */
    @GET(WanApi.API_GET_MY_INTEGRAL)
    suspend fun getMyIntegral(): ApiResponse<IntegralBean>

    /**
     * 获取我的积分获得详情
     * @param page Int 页码，开始位置：1
     * @return ApiResponse<PageBean<IntegralInfoBean>>
     */
    @GET(WanApi.API_GET_MY_INTEGRAL_LIST)
    suspend fun getMyIntegralList(@Path("page") page: Int): ApiResponse<PageBean<IntegralInfoBean>>

    /**
     * 获取积分排行榜
     * @param page Int 页码，开始位置：1
     * @return ApiResponse<PageBean<IntegralBean>>
     */
    @GET(WanApi.API_GET_RANK_LIST)
    suspend fun getIntegralRankList(@Path("page") page: Int): ApiResponse<PageBean<IntegralBean>>

    /**
     * 获取banner数据
     * @return ApiResponse<List<BannerBean>>
     */
    @GET(WanApi.API_GET_BANNER)
    suspend fun getBanner(): ApiResponse<List<BannerBean>>

    /**
     * 获取置顶文章集合数据
     * @return ApiResponse<List<ArticleBean>>
     */
    @GET(WanApi.API_GET_TOP_ARTICLE_LIST)
    suspend fun getTopArticleList(): ApiResponse<List<ArticleBean>>

    /**
     * 获取首页文章数据
     * @param page Int 页码，开始位置：0
     * @return ApiResponse<PageBean<ArticleBean>>
     */
    @GET(WanApi.API_GET_ARTICLE_LIST)
    suspend fun getArticleList(@Path("page") page: Int): ApiResponse<PageBean<ArticleBean>>


    /**
     * 项目分类标题
     * @return ApiResponse<List<CategoryBean>>
     */
    @GET(WanApi.API_GET_PROJECT_CATEGORY)
    suspend fun getProjectCategories(): ApiResponse<List<CategoryBean>>

    /**
     * 根据分类id获取项目数据
     * @param page Int 页码，开始位置：0
     * @param categoryId Int 分类id
     * @return ApiResponse<PageBean<ProjectBean>>
     */
    @GET(WanApi.API_GET_PROJECT_LIST)
    suspend fun getProjectList(@Path("page") page: Int, @Query("cid") categoryId: Int): ApiResponse<PageBean<ProjectBean>>

    /**
     * 获取最新项目数据
     * @param page Int 页码，开始位置：0
     * @return ApiResponse<PageBean<ProjectBean>>
     */
    @GET(WanApi.API_GET_LATEST_PROJECT_LIST)
    suspend fun getLatestProjectList(@Path("page") page: Int): ApiResponse<PageBean<ProjectBean>>

    /**
     * 广场列表数据
     * @param page Int 页码，开始位置：0
     * @return ApiResponse<PageBean<ArticleBean>>
     */
    @GET(WanApi.API_GET_PLAZA_LIST)
    suspend fun getPlazaList(@Path("page") page: Int): ApiResponse<PageBean<ArticleBean>>

    /**
     * 问答列表数据
     * @param page Int 页码，开始位置：0
     * @return ApiResponse<PageBean<ArticleBean>>
     */
    @GET(WanApi.API_GET_QA_LIST)
    suspend fun getQAList(@Path("page") page: Int): ApiResponse<PageBean<ArticleBean>>

    /**
     * 获取体系数据
     * @return ApiResponse<List<TreeBean>>
     */
    @GET(WanApi.API_GET_TREE_LIST)
    suspend fun getTreeList(): ApiResponse<List<TreeBean>>

    /**
     * 知识体系下的文章数据
     * @param page Int 页码，开始位置：0
     * @param cid Int 体系id
     * @return ApiResponse<PageBean<ArticleBean>>
     */
    @GET(WanApi.API_GET_TREE_CHILD_LIST)
    suspend fun getTreeChildList(@Path("page") page: Int, @Query("cid") cid: Int): ApiResponse<PageBean<ArticleBean>>

    /**
     * 获取导航数据
     * @return ApiResponse<List<NavigationBean>>
     */
    @GET(WanApi.API_GET_NAVIGATION_LIST)
    suspend fun getNavigationList(): ApiResponse<List<NavigationBean>>


    /**
     * 公众号分类
     * @return ApiResponse<List<CategoryBean>>
     */
    @GET(WanApi.API_GET_PUBLIC_NUM_LIST)
    suspend fun getPublicNumList(): ApiResponse<List<CategoryBean>>

    /**
     * 获取公众号数据
     * @param page Int 页码，开始位置：0
     * @param id Int 公众号id
     * @return ApiResponse<PageBean<ArticleBean>>
     */
    @GET(WanApi.API_GET_PUBLIC_NUM_ARTICLE_LIST)
    suspend fun getPublicNumArticleList(@Path("page") page: Int, @Path("id") id: Int): ApiResponse<PageBean<ArticleBean>>

    /**
     * 收藏站内文章
     * @param id Int 内容id
     * @return ApiResponse<String?>
     */
    @POST(WanApi.API_COLLECT)
    suspend fun collect(@Path("id") id: Int): ApiResponse<String?>

    /**
     * 取消收藏站内文章
     * @param id Int 收藏id
     * @return ApiResponse<String?>
     */
    @POST(WanApi.API_UN_COLLECT)
    suspend fun unCollect(@Path("id") id: Int): ApiResponse<String?>

    /**
     * 收藏页取消收藏
     * @param id Int 收藏id，拼接在链接上
     * @param originId Int 代表的是你收藏之前的那篇文章本身的id； 但是收藏支持主动添加，这种情况下，没有originId则为-1
     * @return ApiResponse<String?>
     */
    @FormUrlEncoded
    @POST(WanApi.API_INFO_UN_COLLECT)
    suspend fun infoUnCollect(@Path("id") id: Int, @Field("originId") originId: Int): ApiResponse<String?>

    /**
     * 收藏网址
     * @param name String 网址名称
     * @param link String 地址
     * @return ApiResponse<CollectUrlBean>
     */
    @POST(WanApi.API_COLLECT_URL)
    suspend fun collectUrl(
        @Query("name") name: String,
        @Query("link") link: String
    ): ApiResponse<CollectUrlBean>

    /**
     * 取消收藏网址
     * @param id Int 收藏id
     * @return ApiResponse<String?>
     */
    @POST(WanApi.API_UN_COLLECT_URL)
    suspend fun unCollectUrl(@Query("id") id: Int): ApiResponse<String?>

    /**
     * 获取收藏文章数据
     * @param page Int 页码，开始位置：0
     * @return ApiResponse<PageBean<CollectBean>>
     */
    @GET(WanApi.API_GET_COLLECT_LIST)
    suspend fun getCollectList(@Path("page") page: Int): ApiResponse<PageBean<CollectBean>>

    /**
     * 获取收藏网址数据
     * @return ApiResponse<List<CollectUrlBean>>
     */
    @GET(WanApi.API_GET_COLLECT_URL_LIST)
    suspend fun getCollectUrlList(): ApiResponse<List<CollectUrlBean>>

}
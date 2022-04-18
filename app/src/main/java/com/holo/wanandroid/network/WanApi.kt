package com.holo.wanandroid.network

/**
 *
 *
 * @Author holo
 * @Date 2022/4/15
 */
object WanApi {

    /**
     * 默认请求超时时间
     */
    const val DEFAULT_TIMEOUT: Long = 10L

    const val API_BASE = "https://www.wanandroid.com"

    const val API_LOGIN = "user/login"

    const val API_LOGOUT = "user/logout/json"

    const val API_REGISTER = "user/register"

    const val API_GET_MY_INTEGRAL = "lg/coin/userinfo/json"

    const val API_GET_MY_INTEGRAL_LIST = "lg/coin/list/{page}/json"

    const val API_GET_RANK_LIST = "coin/rank/{page}/json"

    const val API_GET_BANNER = "banner/json"

    const val API_GET_TOP_ARTICLE_LIST = "article/top/json"

    const val API_GET_ARTICLE_LIST = "article/list/{page}/json"

    const val API_GET_PROJECT_CATEGORY = "project/tree/json"

    const val API_GET_PROJECT_LIST = "project/list/{page}/json"

    const val API_GET_LATEST_PROJECT_LIST = "article/listproject/{page}/json"

    const val API_GET_PLAZA_LIST = "user_article/list/{page}/json"

    const val API_GET_QA_LIST = "wenda/list/{page}/json"

    const val API_GET_TREE_LIST = "tree/json"

    const val API_GET_TREE_CHILD_LIST = "article/list/{page}/json"

    const val API_GET_NAVIGATION_LIST = "navi/json"

    const val API_GET_PUBLIC_NUM_LIST = "wxarticle/chapters/json"

    const val API_GET_PUBLIC_NUM_ARTICLE_LIST = "wxarticle/list/{id}/{page}/json"

    const val API_COLLECT = "lg/collect/{id}/json"

    const val API_UN_COLLECT = "lg/uncollect_originId/{id}/json"

    const val API_INFO_UN_COLLECT = "lg/uncollect/{id}/json"

    const val API_COLLECT_URL = "lg/collect/addtool/json"

    const val API_UN_COLLECT_URL = "lg/collect/deletetool/json"

    const val API_GET_COLLECT_LIST = "lg/collect/list/{page}/json"

    const val API_GET_COLLECT_URL_LIST = "lg/collect/usertools/json"
}
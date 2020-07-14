package com.tw.wan.api

import com.thp.library.net.HttpResp
import com.tw.wan.bean.*
import retrofit2.http.*

/**
 * @author thp
 * time 2020/4/24
 * desc:
 */
interface WanApi {
    /**
     * 登录
     */
    @POST("/user/login")
    suspend fun loginUser(
        @Query("username") username: String,
        @Query("password") password: String
    ): HttpResp<UserBean>

    /**
     * 注册
     */
    @POST("/user/register")
    suspend fun registerUser(
        @Query("username") username: String, @Query("password") password: String,
        @Query("repassword") repassword: String
    ): HttpResp<UserBean>

    /**
     * 收藏列表
     */
    @GET("/lg/collect/list/{pageNum}/json")
    suspend fun getCollectList(@Path("pageNum") page: Int): HttpResp<ArticlePage>

    /**
     * 收藏
     */
    @POST("/lg/collect/{id}/json")
    suspend fun atCollect(@Path("id") id: Int): HttpResp<String>

    /**
     * 取消收藏
     */
    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun unCollect(@Path("id") id: Int): HttpResp<String>

    /**
     *积分排行榜
     */
    @GET("/coin/rank/{pageNum}/json")
    suspend fun getRank(@Path("pageNum") pageNum: Int): HttpResp<Rank>

    /**
     *  个人积分
     */
    @GET("/lg/coin/userinfo/json")
    suspend fun getPersonRank(): HttpResp<RankItem>

    /**
     * TODO列表
     */
    @GET("/lg/todo/v2/list/{pageNum}/json")
    suspend fun loadTodoData(
        @Path("pageNum") pageNum: Int,
        @QueryMap map: HashMap<String, Any>
    ): HttpResp<Todo>

    /**
     * 增加一个TODO
     */
    @POST("/lg/todo/add/json")
    suspend fun addTodo(
        @QueryMap map: HashMap<String, Any>
    ): HttpResp<Any>

    @POST("/lg/todo/update/{id}/json")
    suspend fun updateTodo(
        @Path("id") id: Int,
        @QueryMap map: HashMap<String, Any>
    ): HttpResp<Any>

    /**
     * 删除一个TODO
     */
    @POST("/lg/todo/delete/{id}/json")
    suspend fun deleteTodo(
        @Path("id") id: Int
    ): HttpResp<Any>

    /**
     * 仅更新完成状态Todo
     */
    @POST("/lg/todo/done/{id}/json")
    suspend fun doneTodo(
        @Path("id") id: Int, @Query("status") status: Int
    ): HttpResp<Any>

    /**
     *  首页banner
     */
    @GET("banner/json")
    suspend fun getBanner(): HttpResp<List<BannerBean>>

    /**
     * 置顶文章
     */
    @GET("/article/top/json")
    suspend fun getTopArticleList(): HttpResp<List<Article>>

    /**
     *  首页文章列表
     */
    @GET("article/list/{pageNo}/json")
    suspend fun getArticleList(@Path("pageNo") pageNo: Int): HttpResp<ArticlePage>

    /**
     *体系数据
     */
    @GET("tree/json")
    suspend fun getSystemData(): HttpResp<List<SystemBean>>

    /**
     *  知识体系下的文章
     *  cid 分类的id，上述二级目录的id
     *  页码：拼接在链接上，从0开始。
     */
    @GET("/article/list/{pageNum}/json")
    suspend fun getSystemListData(
        @Path("pageNum") pageNum: Int,
        @Query("cid") id: Int?
    ): HttpResp<SystemDetail>

    /**
     * 导航数据
     */
    @GET("navi/json")
    suspend fun getNavigationData(): HttpResp<List<NavBean>>

    /**
     * 项目类目列表
     * 项目为包含一个分类，该接口返回整个分类。
     */
    @GET("project/tree/json")
    suspend fun getProjects(): HttpResp<List<ProjectBean>>

    /**
     * 某一个分类下项目列表数据，分页展示
     */
    @GET("/project/list/{pageNum}/json")
    suspend fun getProjectArticles(
        @Path("pageNum") pageNum: Int,
        @Query("cid") cid: Int
    ): HttpResp<ArticlePage>

    /**
     * 获取公众号列表
     */
    @GET("/wxarticle/chapters/json")
    suspend fun getWxArticleTab(): HttpResp<List<Wxarticle>>

    /**
     * 查看某个公众号历史数据
     */
    @GET("/wxarticle/list/{cid}/{pageNum}/json")
    suspend fun getWxArticleList(
        @Path("cid") cid: Int,
        @Path("pageNum") page: Int
    ): HttpResp<ArticlePage>

    @GET("/hotkey/json")
    suspend fun getHotData(): HttpResp<List<HotBean>>

    @POST("/article/query/{pageNum}/json")
    suspend fun getSearchData(
        @Path("pageNum") pageNum: Int,
        @Query("k") key: String
    ): HttpResp<ArticlePage>
}
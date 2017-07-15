package io.github.tonnyl.mango.retrofit

import io.github.tonnyl.mango.data.Project
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by lizhaotailang on 2017/7/10.
 */

interface ProjectService {

    @GET("/projects/{project_id}")
    fun getProject(@Path("project_id") projectId: Long): Observable<Response<Project>>

    @GET("/user/projects")
    fun listProjects(@Query("per_page") perPage: Int = ApiConstants.PER_PAGE,
                     @Query("page") page: Int): Observable<Response<MutableList<Project>>>

    @GET("/users/{user_id}/projects")
    fun listProjectsOfUser(@Path("user_id") userId: Long,
                           @Query("per_page") perPage: Int = ApiConstants.PER_PAGE,
                           @Query("page") page: Int): Observable<Response<MutableList<Project>>>

}
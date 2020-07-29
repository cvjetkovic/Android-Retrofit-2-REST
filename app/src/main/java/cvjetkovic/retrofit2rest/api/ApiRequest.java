package cvjetkovic.retrofit2rest.api;

import cvjetkovic.retrofit2rest.api.response.ItemListResponse;
import cvjetkovic.retrofit2rest.api.response.StatusResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Vladimir Cvjetkovic
 */
public interface ApiRequest {

    @GET("item")
    Call<ItemListResponse> getItem();

    @FormUrlEncoded
    @POST("item")
    Call<StatusResponse> postItem(@Field("name") String name,
                                  @Field("price") String price);

    @FormUrlEncoded
    @PUT("item/{id}")
    Call<StatusResponse> putItem(@Path("id") String id,
                                 @Field("name") String name,
                                 @Field("price") String price);

    //    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "item/{id}", hasBody = true)
    Call<StatusResponse> deleteItem(@Path("id") String id);

}

# ygst_sob_login_demo_mvvm 项目介绍（MVVM 框架）
-------------------------------

使用MVC（最简单）的版本，想简单点的可以直接去这里 https://github.com/gsrkuang/ygst_sob_login_demo

使用MVP（java）框架的版本，直接去这里 https://github.com/gsrkuang/ygst_sob_login_demo_mvp

使用MVP（java）框架的版本，去这里 https://github.com/gsrkuang/ygst_sob_login_demo_mvp

使用MVVM（kotlin）框架的版本，去这里 https://github.com/gsrkuang/kotlin_ygst_sob_login_demo_mvvm


不知道有没有小伙伴，跟我一样，刚刚接触阳光沙滩就卡在了接入登陆API上的，这个项目主要是一个简单的登录api接入例子，代码比较简单。

阳光沙滩的登录demo，没有其他的框架，代码是Java用到okHttp发送请求，使用Glide加载验证码的图片。

登陆主要是要在post的时候用到的三个参数，账号、密码、验证码，还有一个就是请求头参数l_c_i
```java
private void submitLogin(String username ,String password ,String verifyCode){

        String url = Constants.SUNNY_BEACH_API_BASE_URL+"uc/user/login/";

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"phoneNum\": \""+username +"\",\r\n    \"password\": \""+password +"\"\r\n}");
        Request request = new Request.Builder()
                //请求地址
                .url(url+verifyCode)
                //POST方式
                .method("POST", body)
                //增加请求头
                .addHeader("l_c_i", RequestInterceptor.sobCaptchaKey.toString())
                .addHeader("Content-Type", "application/json")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("Login", "onFailure: ");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                loginResponseText = response.body().string();
                Log.d("Login", "onResponse: " + loginResponseText);
                handler.sendEmptyMessage(1);
            }
        });

    }
```

其中要获取账号、密码、验证码这三个参数比较容易，主要的难点还是在于获取请求头参数l_c_i，其实也不难
首先，让我们先在Postman上面直接请求一下验证码api看看

![image](https://user-images.githubusercontent.com/13102787/149130555-3e71613f-e0d8-444a-8b09-150aa47dc7b0.png)

----------------------

![image](https://user-images.githubusercontent.com/13102787/149130646-1c4a6a77-4d54-42d4-8da8-9200f65c6bb9.png)

获取请求头参数l_c_i
------------------

这样就能够得到请求头参数l_c_i，非常容易，那么让我们回到Android端，如何来获取请求头参数l_c_i？

Android端获取请求头参数l_c_i，在Glide加载验证码图片的时候，Glide使用了OKHttp来访问验证码api。

那么我们需要做的是给Glide一个新的OKHttp对象，并且OKHttp对象带上拦截器，这样就可以获取到返回的的请求头参数l_c_i 。

新建一个类HttpGlideModule ,注意要添加上注解@GlideModule,然后继承AppGlideModule,并且复写方法registerComponents,这样在Glide发送请求的时候,就会使用带自定义拦截器的OkHttp对象了。

```java
@GlideModule
public class HttpGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);

        // 请求拦截器
        RequestInterceptor requestInterceptor = new RequestInterceptor();

        OkHttpClient mClient = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build();

        // 注意这里传入新的mClient实例传入即可
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(mClient));

    }
}
```

再来看看RequestInterceptor拦截器
```java
public class RequestInterceptor implements Interceptor {

    public static String sobCaptchaKey = "";
    private static final String SOB_CAPTCHA_KEY_NAME = "l_c_i";

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        Response response = chain.proceed(builder.build());
        Headers responseHeaders = response.headers();

        String url = request.url().toString();

        if (url.contains("uc/ut/captcha")) {
            sobCaptchaKey = responseHeaders.get(SOB_CAPTCHA_KEY_NAME);
            Log.e("sobCaptchaKey" , sobCaptchaKey);
        }

        return response;
    }
}
```

通过上面的拦截器，我们就能得到请求头l_c_i参数值了，到这里登陆用到的所有参数都齐全了。
接下来使用OKHttp来发送POST登陆请求就好，也就是上面的submitLogin方法，好了登陆就完成了，看下效果吧。

效果
----
![image](https://user-images.githubusercontent.com/13102787/149138835-f40aed88-377b-49bd-ba03-c2dd204880bb.png)



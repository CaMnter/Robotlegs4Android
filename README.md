Robotlegs4Androird
==


---

## Robotlegs4Android MVC

This is Robotlegs4Android MVC flow chart（这是Robotlegs4Android 的 MVC 流程图）。

- **EventDispatcher To complete the communication between each layer .   （EventDispatcher 完成各层之间的通信）。**
- **Mediator agent business View layer, View layer only shows a simple lifecycle callback methods  （Mediator 代理 View 层的业务，View层只显示简单的生命周期回调方法）。**
- **Command as a Controller layer, send it to the View of corresponding processing business  （Command 作为 Controller 层，对 View 传过来的业务进行相应处理）。**
- **The Actor as the Model layer, to provide the DB request for the Command, including the SQLite or network requests.  （Actor 作为 Model 层，为 Command 提供 DB 的请求，包括SQLite 或 网络请求。）**

![robotlegs4android_mvc](https://github.com/CaMnter/Robotlegs4Android/raw/master/readme/robotlegs4android_mvc.jpg)


---

## Bolg

[Robotlegs4Android](http://blog.csdn.net/qq_16430735/article/details/49788259)

---

## Robotlegs4Android Gradle

```Gradle
dependencies {
    compile 'com.camnter.robotlegs4android:robotlegs4android:1.0'
}
```

---



## Robotlegs4Android Model

`extends Actor`

Define a Robotlegs4Android Model object, only needs to extends the Actor. 
定义一个Robotlegs4Android Model对象，只需要继承 **Actor** 即可）。

**UserModel**
```Java
public class UserModel extends Actor {

    public void login(String name,String password) {

        // TODO Do you want to network requests

    }

    public boolean logout(){

        // TODO Do you want to network requests

        return true;
    }


}
```

**User**
```Java
public class User implements Serializable {

    public String name;

    public String sign;

}
```


---

## Robotlegs4Android View 

- **Robotlegs4Android Activity**
- **Robotlegs4Android Fragment**

---

### Robotlegs4Android Activity

- **If you don't need this Activity fragments  （如果你这个Activity不需要Fragment）。** `extends RobotlegsActivity`。
- **If you need this Activity fragments  （如果你这个Activity需要Fragment。）** `extends RobotlegsFragmentActivity`。

**MainActivity** 
（Look really is very simple - 看起来真的很简单）
```Java
public class MainActivity extends RobotlegsFragmentActivity {

    /**
     * Please set the fragment layout id
     * 请设置Fragment的布局Id
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
```

**MainActivityMediator**  
（Here to deal with the Activity of things to do - 这里处理Activity要做的事情）
```Java
public class MainActivityMediator extends Mediator {

    private static final String TAG = "MainActivityMediator";

    private MainActivity activity;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    /**
     * {@inheritDoc}
     * {@linkplain IMediator #onRegister}
     */
    @Override
    public void onRegister() {
        this.activity = (MainActivity) this.getViewComponent();
        this.initViews();
        this.initData();
    }

    private void initViews() {
        this.tabLayout = (TabLayout) this.activity.findViewById(R.id.tab_layout_tl);
        this.viewPager = (ViewPager) this.activity.findViewById(R.id.view_pager_vp);
    }

    private void initData() {
        String[] tabTitles = {"ONE", "TWO", "THR", "FOU"};
        Fragment[] fragments = {
                TabLayoutFirstFragment.getInstance(),
                TabLayoutSecondFragment.getInstance(),
                TabLayoutThirdFragment.getInstance(),
                TabLayoutFourthFragment.getInstance()
        };
        MainActivityAdapter adapter = new MainActivityAdapter(this.activity.getSupportFragmentManager(), fragments, tabTitles);
        this.viewPager.setAdapter(adapter);
        this.tabLayout.setupWithViewPager(this.viewPager);
    }

}
```

---

### Robotlegs4Android Fragment

`extends RobotlegsFragment`

**TabLayoutFirstFragment**  
（Look really is very simple - 看起来真的很简单）
```Java
public class TabLayoutFirstFragment extends RobotlegsFragment {

    private static TabLayoutFirstFragment instance;

    private TabLayoutFirstFragment() {
    }

    public static TabLayoutFirstFragment getInstance() {
        if (instance == null) instance = new TabLayoutFirstFragment();
        return instance;
    }

    /**
     * Please set the fragment layout id
     * 请设置Fragment的布局Id
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.tablayout_first_fragment;
    }

}
```

**TabLayoutFirstFragmentMediator**   
（Deal with fragments of things to do here - 这里处理Fragment要做的事情）
```Java
public class TabLayoutFirstFragmentMediator extends Mediator implements View.OnClickListener {

    public TabLayoutFirstFragment fragment;
    public FragmentActivity activity;

    private Button firstBT;
    private TextView firstTV;
    private ImageView firstIV;
    private TextView controllerTV;


    /**
     * {@inheritDoc}
     * {@linkplain IMediator #onRegister}
     */
    @Override
    public void onRegister() {
        this.fragment = (TabLayoutFirstFragment) this.getViewComponent();

        this.activity = this.fragment.getActivity();
        this.initViews();
        this.initData();
    }

    private void initViews() {
        this.firstBT = (Button) this.fragment.self.findViewById(R.id.first_bt);
        this.firstTV = (TextView) this.fragment.self.findViewById(R.id.first_tv);
        this.firstIV = (ImageView) this.fragment.self.findViewById(R.id.first_iv);
        this.controllerTV = (TextView) this.fragment.self.findViewById(R.id.first_controller_tv);
    }

    private void initData() {
        this.firstTV.setText("The ONE created by robotlegs4android frame");
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_bt: {
            }
        }
    }
    
}
```

---

## Robotlegs4Androird Event

`extends com.camnter.robotlegs4android.base.Event`

In order to the communication between each layer, define an Event type  
（为了各个层之间的通信，定义一个Event类型）。

**LoginEvent**
```Java
public class LoginEvent extends Event {

    public static final String USER_LOGIN = "user_login";
    public static final String USER_LOGOUT = "user_logout";

    public static final String USER_LOGIN_SUCCESS_FROM_MODEL_TO_CONTROLLER = "user_login_success_from_model_to_controller";
    public static final String USER_LOGIN_SUCCESS_FROM_MODEL_TO_VIEW = "user_login_success_from_model_to_view";
    public static final String USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW = "user_login_success_from_controller_to_view";

    public String name;
    public String password;

    public User user;

    public LoginEvent(String type) {
        super(type);
    }
    
}
```

---

## Robotlegs4Android Controller

`extends Command`

With communication, nature also should have the Controller to deal with, sent to the Controller.  
（有了通讯，自然也要有Controller去处理，发向Controller的事件）。

**Login**
```Java
public class Login extends Command {

    private static final String TAG = "Login";

    @Inject
    public UserModel userModel;

    @Inject
    public LoginEvent event;

    /**
     * TODO - The Command subclass must inherit the execute method
     * 备忘录 - Command子类必须继承execute方法
     */
    @Override
    public void execute() {
        switch (event.getType()) {
            case LoginEvent.USER_LOGIN: {
                userModel.login(event.name, event.password);
                break;
            }
            case LoginEvent.USER_LOGOUT: {
                userModel.logout();
                break;
            }
            case LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_CONTROLLER: {
                break;
            }
        }
    }


```

---

## !!!!!! Robotlegs4Android Context

`extends com.camnter.robotlegs4android.mvcs.Context`

**Must define com.camnter.robotlegs4android.mvcs.Context**
**必须定义一个com.camnter.robotlegs4android.mvcs.Context**

- **1.In order to configure the View and ViewMediator  （为了配置View和对应Mediator）。**
- **2.In order to singleton injection Model - Actor  （为了单例初始化Model - Actor）。**
- **3.In order to configure the incoming Controller -command events  （为了配置传入Controller - Command 的事件）。**

**MainContext**
```Java
public class MainContext extends Context {

    public MainContext(Object contextView, Boolean autoStartup) {
        super(contextView, autoStartup);
    }

    /**
     * set your mvc relation
     * 设置你的mvc关系
     * <p/>
     * Add the view map
     * Link the View and View the corresponding Mediator
     * 添加view映射
     * 将View 和 View 对应的 Mediator 联系起来
     * <p/>
     * Injection as an singleton, instantiate the singleton
     * 注入实例，实例化单例
     * <p/>
     * Add Event (Event) with the connection of the Command
     * 添加事件（Event）与Command的联系
     */
    @Override
    public void setMvcRelation() {

        /*
         * view映射
         * 将View 和 View 对应的 Mediator 联系起来
         * Add the view map
         * Link the View and View the corresponding Mediator
         */
        this.getMediatorMap().mapView(MainActivity.class, MainActivityMediator.class, null, true, true);
        this.getMediatorMap().mapView(TabLayoutFirstFragment.class, TabLayoutFirstFragmentMediator.class, null, true, true);
        this.getMediatorMap().mapView(TabLayoutSecondFragment.class, TabLayoutSecondFragmentMediator.class, null, true, true);
        this.getMediatorMap().mapView(TabLayoutThirdFragment.class, TabLayoutThirdFragmentMediator.class, null, true, true);
        this.getMediatorMap().mapView(TabLayoutFourthFragment.class, TabLayoutFourthFragmentMediator.class, null, true, true);

        /*
         * 注入实现 实例化单例
         * Injection as an singleton, instantiate the singleton
         */
        this.getInjector().mapSingleton(UserModel.class, "");

        /*
         * 添加事件与Command的联系
         * Add Event (Event) with the connection of the Command
         */
        this.getCommandMap().mapEvent(LoginEvent.USER_LOGIN, Login.class,
                null, false);
        this.getCommandMap().mapEvent(LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_CONTROLLER, Login.class, null, false);

    }

}
```
---

##!!!!!! Robotlegs4Android Application

`extends RobotlegsApplication`

**Must define a Robotlegs4Android Application to initialize the corresponding Robotlegs4Android Context**  
**必须定义一个Robotlegs4Android Application去初始化对应的Robotlegs4Android Context**

**MainApplication**
```Java
public class MainApplication extends RobotlegsApplication {

    private static MainApplication ourInstance = new MainApplication();

    public static MainApplication getInstance() {
        return ourInstance;
    }


    /**
     * Please write your custom robotlegs4android context
     * 请填写你自定义的robotlegs4android context
     * TODO After write your custom robotlegs4android context, please don't call this method
     * TODO 填写完你自定义的robotlegs4android context后，请不要调用此方法
     *
     * @return
     */
    @Override
    protected Context getMvcContextInstance() {
        return new MainContext(this, true);
    }

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if (ourInstance == null) ourInstance = this;
    }

}
```

**AndroidManifest.xml**
```XML
    <application
        android:name="com.camnter.robotlegs4android.test.application.MainApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme">
        <activity
            android:name=".view.activity.MainActivity"
            android:label="@string/app_name">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
```

---

## Event View to Controller

**TabLayoutFirstFragmentMediator** Send an LoginEvent.USER_LOGIN type of LoginEvent.   
**TabLayoutFirstFragmentMediator** 发送一个LoginEvent.USER_LOGIN的LoginEvent。
```Java
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_bt: {
                /*
                 * you can send a your custom event from Model layer to Controller layer,and the
                 * frame will search the event configuration from your custom context
                 * 你可以发送一个你自定义的事件从Model层到Controller层，并且框架会去你自定义的context搜索
                 * 这个事件的配置
                 */
                LoginEvent loginEvent = new LoginEvent(LoginEvent.USER_LOGIN);
                loginEvent.name = "CaMnter";
                loginEvent.password = "Save you from anything";
                this.dispatch(loginEvent);
            }
        }
    }
```

**MainContext** Set up a LoginEvent.USER_LOGIN type of event to the Controller.  
**MainContext** 设置了LoginEvent.USER_LOGIN事件的去向的Controller。
```Java
        this.getCommandMap().mapEvent(LoginEvent.USER_LOGIN, Login.class,
                null, false);
```

**Login** 的`execute()`方法能拿到LoginEvent.USER_LOGIN事件。
```Java
    @Inject
    public LoginEvent event;


    /**
     * TODO - The Command subclass must inherit the execute method
     * 备忘录 - Command子类必须继承execute方法
     */
    @Override
    public void execute() {
        switch (event.getType()) {
            case LoginEvent.USER_LOGIN: {
                userModel.login(event.name, event.password);
                break;
            }
            case LoginEvent.USER_LOGOUT: {
                userModel.logout();
                break;
            }
            case LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_CONTROLLER: {
                Log.i(TAG, "This Login Controller know the user login success");

                /*
                 * send an USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW type of event to View layer
                 * 发送一个USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW类型的事件到View层
                 */
                this.dispatch(new LoginEvent(LoginEvent.USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW));
                break;
            }
        }
    }
```

---

## Event Model to Controller

**UserModel** 的`login(String name,String password)` method send an LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_CONTROLLER type of LoginEvent。  

**UserModel** 的`login(String name,String password)`方法发送一个LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_CONTROLLER的LoginEvent。
```Java
    public void login(String name,String password) {

        // TODO Do you want to network requests

        User user = new User();
        user.name = "CaMnter";
        user.sign = "Save you from anything";

        /*
         * you can send a your custom event from Model layer to Controller layer
         * 你可以发送一个你自定义的事件从Model层到Controller层
         */
        this.dispatch(new LoginEvent(LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_CONTROLLER));
    }
```

---

## Event Model to View

**UserModel** 的`login(String name,String password)` send an LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_VIEW type of LoginEvent again。  


**UserModel** 的`login(String name,String password)`方法再发送一个LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_VIEW的LoginEvent。

```Java
    public void login(String name,String password) {

        // TODO Do you want to network requests

        User user = new User();
        user.name = "CaMnter";
        user.sign = "Save you from anything";

        /*
         * you can send a your custom event from Model layer to View layer
         * 你可以发送一个你自定义的事件从Model层到View层
         */
        LoginEvent loginEvent = new LoginEvent(LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_VIEW);
        loginEvent.user = user;
        this.dispatch(loginEvent);
        /*
         * you can send a your custom event from Model layer to Controller layer
         * 你可以发送一个你自定义的事件从Model层到Controller层
         */
        this.dispatch(new LoginEvent(LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_CONTROLLER));
    }
```

`Mediator.getEventMap().mapListener(IEventDispatcher dispatcher, String type,IListener listener, Class<?> eventClass, Boolean useCapture,int priority, Boolean useWeakReference)`

**TabLayoutFirstFragmentMediator** Add to LoginEvent. USER_LOGIN_SUCCESS_FROM_MODEL_TO_VIEW type of LoginEvent listening .   

**TabLayoutFirstFragmentMediator** 添加对LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_VIEW类型的LoginEvent的监听。
```Java
     */
    @Override
    public void onRegister() {
        this.fragment = (TabLayoutFirstFragment) this.getViewComponent();

        this.activity = this.fragment.getActivity();
        this.initViews();
        this.initData();
        this.initListeners();
    }

    private void initViews() {
        this.firstBT = (Button) this.fragment.self.findViewById(R.id.first_bt);
        this.firstTV = (TextView) this.fragment.self.findViewById(R.id.first_tv);
        this.firstIV = (ImageView) this.fragment.self.findViewById(R.id.first_iv);
        this.controllerTV = (TextView) this.fragment.self.findViewById(R.id.first_controller_tv);
    }

    private void initData() {
        this.firstTV.setText("The ONE created by robotlegs4android frame");
    }

    private void initListeners() {
        this.firstBT.setOnClickListener(this);
        /*
         * listening your custom event（such as listening to an USER_LOGIN_SUCCESS type of LoginEvent）
         * listening from Model layer to View layer in here
         * 监听你的自定义事件（例如监听一个USER_LOGIN_SUCCESS_FROM_MODEL_TO_CONTROLLER_AND_VIEW类型的LoginEvent）
         * 在这里监听从Model层到View层
         */
        this.getEventMap().mapListener(this.getEventDispatcher(), LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_VIEW, new Listener() {
                    /**
                     * {@inheritDoc}
                     * <p/>
                     * {@linkplain IListener #onHandle}
                     *
                     * @param event
                     */
                    @Override
                    public void onHandle(Event event) {
                        if (event instanceof LoginEvent) {
                            TabLayoutFirstFragmentMediator.this.firstIV.setVisibility(View.VISIBLE);
                        }
                    }
                }, null,
                false, 0, true);
    }
```

---

## Event Controller to View
**Login** send an LoginEvent.USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW type of LoginEvent。   
**Login** 发送一个LoginEvent.USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW类型的LoginEvent。
```Java
    /**
     * TODO - The Command subclass must inherit the execute method
     * 备忘录 - Command子类必须继承execute方法
     */
    @Override
    public void execute() {
        switch (event.getType()) {
            case LoginEvent.USER_LOGIN: {
                userModel.login(event.name, event.password);
                break;
            }
            case LoginEvent.USER_LOGOUT: {
                userModel.logout();
                break;
            }
            case LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_CONTROLLER: {
                Log.i(TAG, "This Login Controller know the user login success");

                /*
                 * send an USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW type of event to View layer
                 * 发送一个USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW类型的事件到View层
                 */
                this.dispatch(new LoginEvent(LoginEvent.USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW));
                break;
            }
        }
    }
```

`Mediator.getEventMap().mapListener(IEventDispatcher dispatcher, String type,IListener listener, Class<?> eventClass, Boolean useCapture,int priority, Boolean useWeakReference)`

**TabLayoutFirstFragmentMediator** Add one more to LoginEvent. USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW type of LoginEvent listening.   

**TabLayoutFirstFragmentMediator** 再添加一个对LoginEvent.USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW类型的LoginEvent的监听。
```Java
    /**
     * {@inheritDoc}
     * {@linkplain IMediator #onRegister}
     */
    @Override
    public void onRegister() {
        this.fragment = (TabLayoutFirstFragment) this.getViewComponent();

        this.activity = this.fragment.getActivity();
        this.initViews();
        this.initData();
        this.initListeners();
    }

    private void initViews() {
        this.firstBT = (Button) this.fragment.self.findViewById(R.id.first_bt);
        this.firstTV = (TextView) this.fragment.self.findViewById(R.id.first_tv);
        this.firstIV = (ImageView) this.fragment.self.findViewById(R.id.first_iv);
        this.controllerTV = (TextView) this.fragment.self.findViewById(R.id.first_controller_tv);
    }

    private void initData() {
        this.firstTV.setText("The ONE created by robotlegs4android frame");
    }

    private void initListeners() {
        this.firstBT.setOnClickListener(this);

        /*
         * listening your custom event（such as listening to an USER_LOGIN_SUCCESS type of LoginEvent）
         * listening from Model layer to View layer in here
         * 监听你的自定义事件（例如监听一个USER_LOGIN_SUCCESS_FROM_MODEL_TO_CONTROLLER_AND_VIEW类型的LoginEvent）
         * 在这里监听从Model层到View层
         */
        this.getEventMap().mapListener(this.getEventDispatcher(), LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_VIEW, new Listener() {
                    /**
                     * {@inheritDoc}
                     * <p/>
                     * {@linkplain IListener #onHandle}
                     *
                     * @param event
                     */
                    @Override
                    public void onHandle(Event event) {
                        if (event instanceof LoginEvent) {
                            TabLayoutFirstFragmentMediator.this.firstIV.setVisibility(View.VISIBLE);
                        }
                    }
                }, null,
                false, 0, true);

        /*
         * listening your custom event（such as listening to an USER_LOGIN_SUCCESS type of LoginEvent）
         * listening from Controller layer to View layer in here
         * 监听你的自定义事件（例如监听一个USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW类型的LoginEvent）
         * 在这里监听从Controller层到View层
         */
        this.getEventMap().mapListener(this.getEventDispatcher(), LoginEvent.USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW, new Listener() {
            /**
             * {@inheritDoc}
             * <p/>
             * {@linkplain IListener #onHandle}
             *
             * @param event
             */
            @Override
            public void onHandle(Event event) {
                if (event instanceof LoginEvent) {
                    TabLayoutFirstFragmentMediator.this.controllerTV.setVisibility(View.VISIBLE);
                }
            }
        }, null, false, 0, true);

    }
```

---

## License


Copyright (C) 2015 CaMnter 421482590@qq.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


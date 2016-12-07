package com.woniukeji.jianmerchant.chat;

import android.content.Context;

import com.google.gson.Gson;
import com.woniukeji.jianmerchant.http.BackgroundSubscriber;
import com.woniukeji.jianmerchant.http.HttpMethods;
import com.woniukeji.jianmerchant.http.SubscriberOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;

/**
 * Created by wli on 15/12/4.
 * 实现自定义用户体系
 */
public class CustomUserProvider implements LCChatProfileProvider {

  private static CustomUserProvider customUserProvider;
  private static SubscriberOnNextListener<List<LCChatKitUser>> lcChatKitUserSubscriberOnNextListener;
  private static LCChatProfilesCallBack mCallBack;
  public synchronized static CustomUserProvider getInstance(Context applicationContext) {
    if (null == customUserProvider) {
      customUserProvider = new CustomUserProvider();
      lcChatKitUserSubscriberOnNextListener=new SubscriberOnNextListener<List<LCChatKitUser>>() {
        @Override
        public void onNext(List<LCChatKitUser> lcChatKitUser) {
          mCallBack.done(lcChatKitUser,null);
        }
      };
    }
    return customUserProvider;
  }

  private CustomUserProvider() {
  }

  private static List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();


  @Override
  public void fetchProfiles(Context context, List<String> list, LCChatProfilesCallBack callBack) {
//    Map map=new HashMap();
//    map.put("id",list);
    Gson gson=new Gson();
    String s = gson.toJson(list);
    HttpMethods.getInstance().getTalkUser(context,new BackgroundSubscriber<List<LCChatKitUser>>(lcChatKitUserSubscriberOnNextListener,context),s);
    mCallBack=callBack;
  }

  public List<LCChatKitUser> getAllUsers() {
    return partUsers;
  }
}


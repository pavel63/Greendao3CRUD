package com.pavelwinter.greendao3demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static DaoSession sDaoSession;
    UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "mydb.db");
        Database db = helper.getWritableDb();
        sDaoSession = new DaoMaster(db).newSession();

        mUserDao = sDaoSession.getUserDao();




     //   User user = new User(null, "Alex", 30);

       // User user2 = new User();
       // user2.setName("John");


        // mUserDao.insert(user);
        //mUserDao.insert(user2);

/*

        QueryBuilder<User> queryBuilder = mUserDao.queryBuilder()
                .where(UserDao.Properties.Age.gt(20));
        queryBuilder.build();
        List<User> userList = queryBuilder.list();

        for (int i = 0; i < userList.size(); i++) {

            Log.d("RESULT ", userList.get(i).getName());
        }
*/

//createEntity("Margaria",15);
//readEntity("Alex");
       // updateEntity("Margaria",100);
       // deleteEntity("Margaria");
        //multiQuery("Alex",10);
    }


    /**
     * создаем сущность с таким именем
     */
    void createEntity(String name, int age) {
        User user3 = new User(null, name, age);
        mUserDao.insert(user3);
    }


    /**
     * читаем сущности с этим именем,выводим возраст
     */
    void readEntity(String name) {
        QueryBuilder<User> queryBuilder = mUserDao.queryBuilder()
                .where(UserDao.Properties.Name.eq(name));
        queryBuilder.build();
        List<User> userList = queryBuilder.list();

        for (int i = 0; i < userList.size(); i++) {

            Log.d("RESULT ",String.valueOf(userList.get(i).getAge()));
        }
    }



    /**
     * cтавим новый возраст
     */
    void updateEntity(String name, int newAge) {

        QueryBuilder<User> queryBuilder = mUserDao.queryBuilder()
                .where(UserDao.Properties.Name.eq(name));
        queryBuilder.build();
        List<User> userList = queryBuilder.list();

        for (int i = 0; i < userList.size(); i++) {

            User user= userList.get(i);
            user.setAge(newAge);
            mUserDao.update(user);

        }
    }



    void deleteEntity(String name) {
        QueryBuilder<User> queryBuilder = mUserDao.queryBuilder()
                .where(UserDao.Properties.Name.eq(name));
        queryBuilder.build();
        List<User> userList = queryBuilder.list();

        for (int i = 0; i < userList.size(); i++) {

            long id=userList.get(i).getId();
            mUserDao.deleteByKey(id);

        }

    }



    void multiQuery(String name,int age){

        QueryBuilder<User> queryBuilder = mUserDao.queryBuilder()
                .where(UserDao.Properties.Name.eq(name),UserDao.Properties.Age.le(age));
        queryBuilder.build();
        List<User> userList = queryBuilder.list();

        for (int i = 0; i < userList.size(); i++) {

            Log.d("RESULT ",String.valueOf(userList.get(i).getAge()));
        }
    }
}



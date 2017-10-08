# Greendao3CRUD
Demonstrates working with ORM Greendao v3

Работа с Greendao 3

подключаем зависимости:

пишем в build.gradle dependencies ПРОЕКТА:
classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2'

далее идем в build.gradle МОДУЛЯ:
ставим САМОЙ ПЕРВОЙ СТРОКОЙ файла:
apply plugin: 'org.greenrobot.greendao'

и стандартно в зависимости:
compile 'org.greenrobot:greendao:3.2.2'

далее создаем необходимые нам таблицы

чтобы создать таблицу,нам необходимо просто создать класс с нужным именем,и пометить его Гриндаовской аннотацией @Entity.
Колонками таблицы будут поля нашего класса.
Также обязательно нужно определить поле типа Long и названием id,и пометить его Гриндаовской аннотацией @Id

после генерации наши классы дополнятся сгенерированным кодом,менять кторый нельзя и бессмысленно(сотрется при следующем запуске.Правда,для сохранения измененного кода можно использовать аннотацию @Keep


создадим одну простую таблицу,сохраняющую состоящую из двух(с id-трех) колонок-имя и возраст:

@Entity
public class User {

    @Id
    private Long id;

    private String name;

    private int age;

}


жмем Build-make project

теперь мы можем видеть в нашем же классе сгенеренный код

стандартно подключаем базу:

        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,"mydatabase.db");
        Database db=helper.getWritableDb();
        sDaoSession=new DaoMaster(db).newSession();


получаем дао для каждой нашей энтити-таблицы:
UserDao userDao=sDaoSession.getUserDao();
CREATE
создаем объект класса,нашу сущность и заполняем информацией.

либо в конструктор(обратите внимание,что id ставится как null-т.к он инкрементируется автоматически):

  User user=new User(null,"Alex",30);

либо сеттером:
 User user2=new User();
 user2.setName("John");

ну и добавляем в базу:
mUserDao.insert(user);

READ
делаем запрос.Тут просто извлекаем список имен,которые не null:

  QueryBuilder<User>queryBuilder=mUserDao.queryBuilder()
                .where(UserDao.Properties.Name.isNotNull());
        queryBuilder.build();
        List<User>userList=queryBuilder.list();

и берем стандартными геттерами:

        for (int i=0;i<userList.size();i++){

            Log.d("RESULT ",userList.get(i).getName());
        }

Можно,конечно,задать и другие условия,и даже несколько-
eq()-равен
gt()-больше
.where(UserDao.Properties.Name.eq(“John”)-выведет все сущности с именем John 
или .whree(UserDao.Properties.Age.gt(20)-всех за 20

В случае нескольких условий запроса они перечисляются через запятую:
 QueryBuilder<User> queryBuilder = mUserDao.queryBuilder()
                .where(UserDao.Properties.Name.eq(name),UserDao.Properties.Age.le(age));
        queryBuilder.build();
        List<User> userList = queryBuilder.list();
здесь запрашивается с определенным именем и возрастом ниже указанного
UPDATE
ставим новое значение и вызываем метод update()

 user.setAge(newAge);
            mUserDao.update(user);
DELETE
вычисляем id и удаляем по нему методом deleteByKey():
  long id=userList.get(i).getId();
            mUserDao.deleteByKey(id);

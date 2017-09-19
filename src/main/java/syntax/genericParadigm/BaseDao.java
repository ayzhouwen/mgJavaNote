package syntax.genericParadigm;

/**
 * Created by Administrator on 2017/6/5.
 */
//JFinalShop中关于dao层的演练
public class BaseDao<M extends Model> {
    public  M modelManager; //只能通过构造参数传实例直接赋值,或者class类反射动态新建,没办法直接new 因为编译器不知道要哪个类
    public User user;
    public void set(){
        try {
            this.modelManager= (M) user.getClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}

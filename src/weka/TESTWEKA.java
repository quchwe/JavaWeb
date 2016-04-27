package weka;

import DB.Choose;
import DB.Course;
import DB.Student;
import choosecourse.QueryCourse;
import querysql.AllCourse;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by quchwe on 2016/3/24 0024.
 */
public class TESTWEKA {
    InstanceQuery query = null;
    List<Course> allCourseList = new ArrayList<>();
    AllCourse allCourse = new AllCourse();

    public TESTWEKA(){
        try {
            query = new InstanceQuery();
            allCourseList = allCourse.Query("select *from course where course.type = '公共选修课'");

        } catch (Exception e) {
            e.printStackTrace();
        }

        query.setDatabaseURL("jdbc:sqlserver://localhost:1433;databaseName=Choose_course");
        query.setUsername("sa");
        query.setPassword("1234");
    }

    private List<Course> wekaCourse(String sql,List<Course> tuiJianList){
        query.setQuery(sql);
// if your data is sparse, then you can say so, too:
// query.setSparseData(true);
        Instances data = null;

        SimpleKMeans simpleKMeans = new SimpleKMeans();

        try {
            data = query.retrieveInstances();
            if(!data.checkForStringAttributes()){
                return null;
            }
            simpleKMeans.setSeed(10);
            simpleKMeans.setNumClusters(20);
            simpleKMeans.setMaxIterations(500);
            simpleKMeans.buildClusterer(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        simpleKMeans.getNumClusters();
        Instances tempIns = simpleKMeans.getClusterCentroids();
        System.out.println("CentroIds: " + tempIns.toString());
        String s = tempIns.toString();
        String datas[] = s.split("@data");
        String courseAndProfession[]=  datas[1].split("\n");
        List<String> course = new ArrayList<>();
        for (int i=1;i<courseAndProfession.length;i++){
            course.add(courseAndProfession[i].split(",")[0]);
        }
        List<String> courseList =new ArrayList<>();
        for (int i=0;i<course.size();i++){
            courseList.add(course.get(i));
        }


        for (String c:courseList){
            for (Course course1:allCourseList){
                if (c.equals(course1.getCn())) {
                    if (!tuiJianList.contains(course1))
                        tuiJianList.add(course1);
                }
            }
        }
        return tuiJianList;

//        for (String c:courseList){
//            System.out.println(c);
//        }
    }
    public static void main(String []args){

       try {
          TESTWEKA testweka = new TESTWEKA();
     testweka.query.setQuery(" select course.cn from course where course.cn in( select choosed.cn from choosed where sno in" +
               " (select choosed.sno from choosed where choosed.cn = 'c语言'))and course.profession = '公共选修课' ");
// if your data is sparse, then you can say so, too:
// query.setSparseData(true);
       Instances data = testweka.query.retrieveInstances();
           if(!data.checkForStringAttributes()){
               return;
           }
           SimpleKMeans simpleKMeans = new SimpleKMeans();
           simpleKMeans.setSeed(10);
           simpleKMeans.setNumClusters(20);
           simpleKMeans.setMaxIterations(500);
           simpleKMeans.buildClusterer(data);
           simpleKMeans.getNumClusters();
           Instances tempIns = simpleKMeans.getClusterCentroids();
          System.out.println("CentroIds: " + tempIns.toString());
           String s = tempIns.toString();
           String datas[] = s.split("@data");
          String courseAndProfession[]=  datas[1].split("\n");
           List<String> course = new ArrayList<>();
           for (int i=1;i<courseAndProfession.length;i++){
               course.add(courseAndProfession[i].split(",")[0]);
           }
           List<String> courseList =new ArrayList<>();
           for (int i=0;i<course.size();i++){
               courseList.add(course.get(i));
           }


           for (String c:courseList){
               System.out.println(c);
           }





           //System.out.println(simpleKMeans.getPreserveInstancesOrder());
        //System.out.println(simpleKMeans.globalInfo());
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

    /**
     * 获得推荐课程
     * @param s 需要推荐课程的用户
     * @param chooseList 该用户选课列表
     * @return 推荐课程列表
     */
    public List<Course> getTuiJianCourseList(Student s, List<Choose> chooseList){
        List<Course> tuiJianList = new ArrayList<>();
        String interestCourseSql ="select course.cn from course where fenlei = '"+s.getInterest()+"' and course.profession ='公共选修课’";
       wekaCourse(interestCourseSql,tuiJianList);

        String sameProfessionSql = "Select course.cn from course  where course.cn in(" +
                "Select choosed.cn from choosed where choosed.sno in (select student.sno " +
                "where student.classname  =’"+s.getClassname()+"’) )and course.profession = ‘公共选修课’)";
        wekaCourse(sameProfessionSql,tuiJianList);
        for (Choose c:chooseList) {
            String sameCourseSql = "select course.cn from course where course.cn in( select choosed.cn from choosed where sno in" +
                    " (select choosed.sno from choosed where choosed.cn = '"+c.getCn()+"'))and course.profession = '公共选修课' ";
            wekaCourse(sameCourseSql,tuiJianList);

        }
        return tuiJianList;

    }

}

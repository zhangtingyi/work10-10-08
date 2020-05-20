package test;

import javax.swing.tree.RowMapper;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.util.List;

@Conponent
@Transactional
public class JdbcTest {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    public void insertTest(){

//        jdbcTemplate.update("insert into person(name,carid) value("ssh",1)");
        jdbcTemplate.update("insert into person(name,carid) value(?,?)",new Object[]("ssh",1),new int[]{Types.VARCHAP,Types.Integer});

    }

    public void selectTest(){

        RowMapper rowMapper=new RowMapper(){
            public object mapRow(ResulSet resulSet,int i) throws SQLException{
                PersonEntity personEntity=new PersonEntity();
                personEntity.setName(resulSet.getString("name"));

                return personEntity;
            }
        };

        PersonEntity personEntity=(PersonEntity) jdbcTemplate.queryForObject("select * from person where id=?",new Object[]{123},rowMapper);
        System.out.println(personEntity.getName());
    }

    public List selectAll(){

        RowMapper rowMapper=new RowMapper(){
            public Object mapRow(ResultSet resultSet,int i) throws SQLException{
               PersonEntity personEntity=new PersonEntity();
               personEntity.setName(resultSet.getSpring("name"));
               personEntity.setId(resultSet.getInt("id"));
               personEntity.setCarid(resultSet.getInt("carid"));

                return personEntity;
            }
        };

        List list=jdbcTemplate.query("select * from person where id>?",new Object(1000),rowMapper)

        return list;
    }


    public static void main(String[] args) {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTest jdbcTest=(JdbcTest) applicationContext.getBean("jdbcTest");

        jdbcTest.insertTest();

        jdbcTest.selectTest();

        List list=jdbcTest.selectAll();
        for(Object object:list){
            if(object instanceof PersonEntity){
                PersonEntity personEntity=(PersonEntity) object;
                System.out.println(personEntity);
            }
        }
    }

}

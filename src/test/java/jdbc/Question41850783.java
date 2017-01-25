package jdbc;

import org.junit.Test;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * I have a table with 5 columns (id |state_abbrevation | state_name | area_code | cities ).
 * Now I have to store all the values as key value pair where key= state_abbrevation and Value = area_code & cities.
 * I am currently using JDBC and got whole data in Resultset. How do I do that?
 */
public class Question41850783 {

    @Test
    public void testJdbc() throws Exception {
        DataSource dataSource = createNewDataSource();
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement("select * from table");
        ResultSet resultSet = preparedStatement.executeQuery();
        Map<String, State> result = new HashMap<>();
        while (resultSet.next()) {
            result.put(
                    resultSet.getString("state_abbrevation"),
                    new State(
                            resultSet.getString("area_code"),
                            resultSet.getString("cities")
                    )
            );
        }
    }

    private class State {
        private final String area;
        private final String cities;


        public State(String area, String cities) {
            this.area = area;
            this.cities = cities;
        }

        public String getArea() {
            return area;
        }

        public String getCities() {
            return cities;
        }
    }

    private DataSource createNewDataSource() {
        return null;
    }
}

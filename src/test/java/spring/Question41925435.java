package spring;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jboss.logging.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Question41925435.TestController.class})
@WebMvcTest(Question41925435.TestController.class)
public class Question41925435 {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/sentemail?fullnameClient=bugoga&idBanner=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"idClick\": 3,\n" +
                                "    \"banner\":\n" +
                                "    {\n" +
                                "        \"idBanner\": 1,\n" +
                                "        \"clickSet\": null,\n" +
                                "        \"businesscentr\":\n" +
                                "        {\n" +
                                "            \"idBc\": 1,\n" +
                                "            \"bannerSet\": null,\n" +
                                "            \"email\": \"new@mail.ru\",\n" +
                                "            \"telephoneBc\": \"79220909777\"\n" +
                                "        },\n" +
                                "        \"textBanner\": \"onebanner\",\n" +
                                "        \"filepathBanner\": \"banner\\banner.jpg\",\n" +
                                "        \"timeStart\": 1580210257811,\n" +
                                "        \"timeEnd\": 1485602255190\n" +
                                "    },\n" +
                                "    \"fullnameClient\": \"voav\"\n" +
                                "}")
        )
                .andDo(print());

    }

    public class ServiceImpl {


//        @Transactional
//        public String sent(String fullnameClient, long idBanner) {
//            Banner banner = bannerRepositor.findOne(idBanner);
//            Click click = new Click();
//            click.setBanner(banner);
//            click.setFullnameClient(fullnameClient);
//            clickRepository.save(click);
//        }
//
//        @Query("FROM Click WHERE fullnameClient = :fullnameClient AND banner.idBanner = :idBanner")
//        Click sent(@Param("fullnameClient") String fullnameClient, @Param("idBanner") long idBanner);

    }

    @Controller
    public static class TestController {
        @RequestMapping(value = "/sentemail", method = RequestMethod.POST, produces = "application/json")
        @ResponseBody
        public String sentClick(@RequestBody BannerInfo bannerInfo ) {
            System.out.println(String.format("fullnameClient: %s and idBanner is: %s", bannerInfo.getFullName(), bannerInfo.getBannerId()));
            return "{}";
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class BannerInfo {

            @JsonProperty("banner")
            private Banner banner;

            @JsonProperty("fullnameClient")
            private String fullName;

            public Banner getBanner() {
                return banner;
            }

            public void setBanner(Banner banner) {
                this.banner = banner;
            }


            public Integer getBannerId() {
                return banner.getBannerId();
            }

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Banner {
            @JsonProperty("idBanner")
            private Integer bannerId;

            public Integer getBannerId() {
                return bannerId;
            }

            public void setBannerId(Integer bannerId) {
                this.bannerId = bannerId;
            }
        }
    }
}

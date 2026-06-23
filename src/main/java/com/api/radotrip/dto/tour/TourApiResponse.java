package com.api.radotrip.dto.tour;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class TourApiResponse {
    private Response response;

    @Data
    public static class Response {
        private Header header;
        private Body body;
    }

    @Data
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Data
    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Data
    public static class Items {
        private List<Item> item;
    }

    @Data
    public static class Item {
        private String title;
        private String addr1;
        private String addr2;
        private String contentid;
        private String contenttypeid;
        
        @JsonProperty("mapx")
        private Double mapX;
        
        @JsonProperty("mapy")
        private Double mapY;
    }
}

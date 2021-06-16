package at.ac.fhcampuswien.newsapi.enums;

public enum Endpoint {
    TOPHEADLINES("top-headlines"),

    EVERYTHING("everything");

    private final String endPoint;

    Endpoint(String endPoint){
        this.endPoint = endPoint;
    }

    public String getValue() {
        return endPoint;
    }
}

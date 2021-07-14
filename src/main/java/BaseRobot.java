import java.util.Map;

public abstract class BaseRobot {

        private String rootWebsiteUrl;

        public BaseRobot(String rootWebsiteUrl) {
            this.rootWebsiteUrl = rootWebsiteUrl;
        }

        public String getRootWebsiteUrl() {
            return rootWebsiteUrl;
        }

        public void setRootWebsiteUrl(String rootWebsiteUrl) {
            this.rootWebsiteUrl = rootWebsiteUrl;
        }

        public abstract Map<String, Integer> getWordsStatistics();

        public abstract int countInArticlesTitles(String text);

        public abstract String getLongestArticleTitle();


}

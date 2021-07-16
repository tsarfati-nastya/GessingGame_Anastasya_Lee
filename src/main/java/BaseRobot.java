import java.io.IOException;
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

        public abstract Map<String, Integer> getWordsStatistics() throws IOException;

        public abstract int countInArticlesTitles(String text) throws IOException;

        public abstract String getLongestArticleTitle() throws IOException;


}

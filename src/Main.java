import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    List<ImageFile> imageFileList = new ArrayList<ImageFile>();
    private static DbHandler dbHandler = null;

    public static void main(String[] args) throws IOException, SQLException {

        dbHandler = DbHandler.getInstance("jdbc:sqlite:C:/prod/myfin.db");

        dbHandler.returnLastPostWithDateWhenItPosted("cat");

    }
}

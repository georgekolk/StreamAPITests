import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static DbHandler dbHandler = null;

    public static void main(String[] args) throws IOException, SQLException {

        List<ImageFile> imageFileListToRemove = new ArrayList<ImageFile>();


        File localFile = new File("C:/Program Files/Far Manager/changelog_eng");
        File localDir = new File("C:\\prod\\save\\explore\\tags\\cat");

        List<ImageFile>  imageFileList = Files.walk(Paths.get("C:\\prod\\save\\explore\\tags\\cat"), 1)
                .filter(path -> !path.equals(Paths.get("C:\\prod\\save\\explore\\tags\\cat")))
                //.filter(p -> p.toString().endsWith(".jpg"))
                .map(ImageFile::new).collect(Collectors.toList());


        //Date date = new Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        //String formattedDate = sdf.format(date);
        //System.out.println(formattedDate);
        String currentDate = sdf.format(new Date());



        /*for (File file: localDir.listFiles()) {

            //System.out.println(sdf.format(new Date(file.lastModified())));

            if (sdf.format(new Date(file.lastModified())).equals(date2)){
                System.out.println(sdf.format(new Date(file.lastModified())));
            }


        }*/

        for (ImageFile imageFile : imageFileList) {

            imageFile.setBlog("cat");
            imageFile.setFileNameId(imageFile.getFileName().substring(0, imageFile.getFileName().lastIndexOf('.')));

            String lastAscess = sdf.format(imageFile.getLastModified());
            System.out.println("lastAscess: " + lastAscess + " currentDate :" + currentDate);

                if (!currentDate.equals(lastAscess)){
                    System.out.println("added " + imageFile);
                    imageFileListToRemove.add(imageFile);
                }

            //imageFile.setDate(sdf.format(imageFile.getLastModified()));
            System.out.println(imageFile + " " + imageFile.getDate());

        }


        imageFileList.removeAll(imageFileListToRemove);


        for (ImageFile imageFile : imageFileList) {

            System.out.println("imageFileList after remove: " + imageFile + " " + sdf.format(imageFile.getLastModified()));

        }
        /*

        Arrays.sort(array, new Comparator<String[]>() {
    public int compareTo(String[] one, String[] two) {
         // implement compareTo here
    }
});

         */


      /*  dbHandler = DbHandler.getInstance("jdbc:sqlite:C:/prod/myfin.db");

        dbHandler.returnLastPostWithDateWhenItPosted("cat");











        ImageFile imageFile = new ImageFile(Paths.get("C:\\prod\\save\\explore\\tags\\cat\\69675032_2557988457615067_7050491166932448813_n.jpg"));
        System.out.println("imageFile: " + imageFile.getPath());
        File omgFile = new File(String.valueOf(imageFile.getPath()));


        Path path = Paths.get("C:\\prod\\save\\explore\\tags\\cat\\69675032_2557988457615067_7050491166932448813_n.jpg");

        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        String attrString = attr.creationTime().toString();
        File omgThisFFIle = new File(path.toUri());


        System.out.println("omgThisFFIle lastModified(): " + omgThisFFIle.lastModified());
        System.out.println("omgThisFFIle format lastModified(): " + sdf.format(omgThisFFIle.lastModified()));



        System.out.println("Creation time: " + attr.creationTime().toString());
        System.out.println("Creation time formatted : " + sdf.format(attrString));


*/

    }
}

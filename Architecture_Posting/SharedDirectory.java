package Architecture_Posting;

import java.io.*;
import java.util.*;

public class SharedDirectory {

    static ArrayList filestoshare = new ArrayList();
    public static String listFileIDSaving;

    public SharedDirectory(String sharepath) {
        generateFileList(new File(sharepath));
    }

    public static void generateFileList(File directorytosearch) {
        listFileIDSaving = "";
        String[] filenames = directorytosearch.list(); // All of the files and directories in the current folder.
        for (int i = 0; i < filenames.length; i++) {
            listFileIDSaving += filenames[i] + "\n";
        }
    }

    public static String getListFileIDSaving() {
        return (listFileIDSaving);
    }
}

package com.example.filemeneger_v2.common.splitFileExample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Del {
    public static void main(String[] args) throws IOException {
        Path source = Paths.get(Paths.get("").toAbsolutePath().toString(),"11", "Акция.png");
        Path destinationTmp = Paths.get(Paths.get("").toAbsolutePath().toString(), "cloud-storage-server", "server", "tmp", "log1", "tmp");

        List<String> listTmpFiles = Files.list(destinationTmp)
                .map(file -> file.getFileName().toString())
                .filter(file -> file.contains(source.getFileName().toString()))
                .sorted(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        String[] arr1 = o1.split("\\.");
                        String[] arr2 = o2.split("\\.");

                        int o_1 = Integer.parseInt(arr1[2].substring(3));
                        int o_2 = Integer.parseInt(arr2[2].substring(3));

                        return o_1 - o_2;
                    }
                })
                .collect(Collectors.toList());


        for (String s : listTmpFiles) {
            Path path = Paths.get(destinationTmp.resolve(s).toString());
            if(Files.exists(path)) {
                Files.delete(path);
                System.out.println("Файл удален: " + s);
            }
        }

    }
}

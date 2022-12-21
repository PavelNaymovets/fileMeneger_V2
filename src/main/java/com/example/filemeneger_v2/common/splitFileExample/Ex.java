package com.example.filemeneger_v2.common.splitFileExample;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Ex {
    public static void main(String[] args) throws IOException {
        Path source = Paths.get(Paths.get("").toAbsolutePath().toString(),"11", "Акция.png");

        Path destinationTmp = Paths.get(Paths.get("").toAbsolutePath().toString(), "cloud-storage-server", "server", "tmp", "log1", "tmp");
        Path destination = Paths.get(Paths.get("").toAbsolutePath().toString(),"cloud-storage-server", "server", "tmp", "log1");

        try {
            Files.createDirectories(destination);
            Files.createDirectories(destinationTmp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] buf = new byte[8198];

        BufferedInputStream is = new BufferedInputStream(new FileInputStream(source.toString()));
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(destination.resolve(source.getFileName()).toString()));

        int c = 0;
        int packNumber = 0;

        while((c = is.read(buf)) != -1) {
            if(c < buf.length) {
                buf = Arrays.copyOf(buf, c);
            }
            BufferedOutputStream osTmp = new BufferedOutputStream(new FileOutputStream(destinationTmp.resolve(source.getFileName() + ".tmp" + packNumber).toString()), 8198);
            osTmp.write(buf);
            osTmp.flush();
            packNumber ++;
            System.out.println("Пакетов передано:" + packNumber);
        }

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

        for (String files : listTmpFiles) {
            try (BufferedInputStream isTmp = new BufferedInputStream(new FileInputStream(destinationTmp.resolve(files).toString()), 8198)) {
                int b = 0;
                while ((b = isTmp.read(buf)) != -1) {
                    if (b < buf.length) {
                        buf = Arrays.copyOf(buf, b);
                    }
                    os.write(buf);
                    os.flush();
                }
                System.out.println("Файл записан:" + files);
            }
        }

        System.out.println("Итоговый файл готов");

    }
}

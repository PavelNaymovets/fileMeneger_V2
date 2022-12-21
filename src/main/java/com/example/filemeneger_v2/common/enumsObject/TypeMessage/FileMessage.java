package com.example.filemeneger_v2.common.enumsObject.TypeMessage;

//Класс описывающий пакеты файла отправляемого на сервер. Файл шлется частями
public class FileMessage implements AbstractMessage {

    private String login; //логин пользователя
    private String fileName; //имя файла
    private String dirDestination; //папка в которую нужно передать файл
    private byte[] bytesPackage; //часть файла(пакет) в байтах
    private int numberPackage; //номер пакета
    private int countPackage; //общее количество пакетов

    public FileMessage(String login, String fileName, String dirDestination, byte[] bytesPackage, int numberPackage, int countPackage) {
        this.login = login;
        this.fileName = fileName;
        this.dirDestination = dirDestination;
        this.bytesPackage = bytesPackage;
        this.numberPackage = numberPackage;
        this.countPackage = countPackage;
    }

    public String getLogin() {
        return login;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDirDestination() {
        return dirDestination;
    }

    public byte[] getBytesPackage() {
        return bytesPackage;
    }

    public int getNumberPackage() {
        return numberPackage;
    }

    public int getCountPackage() {
        return countPackage;
    }

    @Override
    public TypeMessage getTypeMessage() {
        return TypeMessage.FILE_MESSAGE;
    }
}

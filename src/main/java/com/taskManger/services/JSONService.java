package com.taskManger.services;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.google.gson.Gson;
import com.taskManger.DataStorage.DataStorage;
import com.taskManger.entities.ListOfTasks;
import com.taskManger.entities.Tasks;
import com.taskManger.entities.User;
import com.taskManger.entities.WatcherForTasks;
import com.taskManger.exception.JsonValidationException;
import lombok.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class JSONService {
    private final String DEFAULT_JSON_FILEPATH = "src/main/resources/auto_created.json";
    private final String DEFAULT_JSON_SCHEMA_FILEPATH = "src/main/resources/schema/schema.json";
    File jsonSchema = new File(DEFAULT_JSON_SCHEMA_FILEPATH);
    String filePath = DEFAULT_JSON_FILEPATH;
    File jsonFile = new File(filePath);

    private final DataStorage dataStorage;

    @Data
    @ToString
    class JSONFormatList{
        List<User> userList;
        List<Tasks> tasksList;
        List<WatcherForTasks> watcherForTasks;
        List<ListOfTasks> listOfTasks;

//        void importFromDataStorage(){
//            this.userList = dataStorage.getUserList();
//            this.tasksList = dataStorage.getTasksList();
//            this.listOfTasks = dataStorage.getListOfTasks();
//            this.watcherForTasks = dataStorage.getWatcherForTasksList();
//        }
//        void exportToDataStorage(){
//            dataStorage.setUserList(this.userList);
//            dataStorage.setTasksList(this.tasksList);
//            dataStorage.setListOfTasks(this.listOfTasks);
//            dataStorage.setWatcherForTasksList(this.watcherForTasks);
//        }
    }



    void importFromDataStorage(JSONFormatList jsonFormatList){
        jsonFormatList.userList = dataStorage.getUserList();
        jsonFormatList.tasksList = dataStorage.getTasksList();
        jsonFormatList.listOfTasks = dataStorage.getListOfTasks();
        jsonFormatList.watcherForTasks = dataStorage.getWatcherForTasksList();
    }
    void exportToDataStorage(JSONFormatList jsonFormatList){
        dataStorage.setUserList(jsonFormatList.getUserList());
        dataStorage.setTasksList(jsonFormatList.getTasksList());
        dataStorage.setListOfTasks(jsonFormatList.getListOfTasks());
        dataStorage.setWatcherForTasksList(jsonFormatList.getWatcherForTasks());
    }



    public JSONService(DataStorage dataStorage) {
        this.dataStorage = dataStorage;

    }


    private void createJsonFile(File file) throws IOException{
        boolean created = file.createNewFile();
        if (created){
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("{}");
            System.out.println("File has been created");
            fileWriter.close();
        }
    }

    private void setFilePath(String filePath) throws NullPointerException{
        if (filePath != null)
            this.filePath = filePath;
        else
            throw new NullPointerException("Filepath must not be null");
    }


    public boolean import_json(String filePath) throws NullPointerException, JsonValidationException {
        setFilePath(filePath);
        jsonFile = new File(filePath);

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.byDefault();

            JsonNode jsonNode = mapper.readTree(jsonSchema);

            com.github.fge.jsonschema.main.JsonSchema jsonSchema =
                    jsonSchemaFactory.getJsonSchema(jsonNode);


            ProcessingReport report = jsonSchema.validate(mapper.readTree(jsonFile));

//                System.out.println(writer.writeValueAsString(schema));
            if (report.isSuccess()){
                System.out.println("horosho");

                Gson gson = new Gson();

                try(FileReader reader = new FileReader(jsonFile)){
                    JSONFormatList jsonFormatList = gson.fromJson(reader, JSONFormatList.class);
                    System.out.println(jsonFormatList);
                    this.exportToDataStorage(jsonFormatList);
//                    jsonFormatList.exportToDataStorage();
                }catch (Exception e){
                    e.printStackTrace();
                }

//                JSONFormatList jsonFormatList = mapper.readValue(jsonFile, JSONFormatList.class);
//                jsonFormatList.exportToDataStorage();

            }else{
                StringBuffer stringBuffer = new StringBuffer("");
                report.forEach(processingMessage -> {
                    System.out.println(processingMessage.getMessage());
                    stringBuffer.append(processingMessage.getMessage());
                    stringBuffer.append("\n");
                });
                throw new JsonValidationException(String.format("File %s failed validation. Reason here:\n%s", jsonFile.getName(), stringBuffer.toString()));
            }



        } catch (ProcessingException | IOException e) {
            System.out.println("exception!!!!");
            e.printStackTrace();
        }

        return true;
    }

    public boolean export_json() throws IOException{
        this.createJsonFile(jsonFile);
        if(jsonFile.canWrite()){
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

            // Java objects to JSON file
            JSONFormatList jsonFormatList = new JSONFormatList();
//            jsonFormatList.importFromDataStorage();
            importFromDataStorage(jsonFormatList);
            writer.writeValue(jsonFile, jsonFormatList);

        }

        return true;
    }

    public boolean export_json(String newFilePath) throws IOException {
        if (newFilePath != null)
            jsonFile = new File(newFilePath);
        this.export_json();
        return true;
    }

    public void save() {
//        if(userJson.canWrite()){
//            ObjectMapper mapper = new ObjectMapper();
//
//            try {
//
//                // Java objects to JSON file
//                mapper.writeValue(userJson, userList);
//
//                // Java objects to JSON string - pretty-print
//                String jsonInString2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userList);
//
//                System.out.println(jsonInString2);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
    }

}

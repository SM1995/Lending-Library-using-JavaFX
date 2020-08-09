package media;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.io.File;
import java.util.Optional;

public class LendingLibraryGUI extends Application {

    GridPane root = new GridPane();
    public TextField tf =new TextField();
    Button add = new Button("add");
    Button delete = new Button("delete");
    Button checkIn = new Button("checkIn");
    Button checkOut = new Button("checkOut");
    TextInputDialog textInput = new TextInputDialog();
    ListView<String> listView= new ListView<>();
    Library library = new Library();
    AlertBox alertBox = new AlertBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        listView.setPrefSize(200, 50);

        File file = new File("library.txt");
        if(file.exists() && file.length()!=0) library.open();

        for (int i=0;i<library.numberOfItems;i++){
            listView.getItems().add(library.items.get(i).getTitle());
        }
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        add.setOnAction(e ->{String title, format;
            title="";
            format="";
            textInput.setTitle("Lending Library");
            textInput.setContentText("What is the title? ");

            Optional<String> result = textInput.showAndWait();
            TextField input = textInput.getEditor();
            if(input.getText()!=null && input.getText().length()!=0){
                title= input.getText();
            }
            textInput.getEditor().clear();
            textInput.setContentText("What is the format? ");

            Optional<String> result1 = textInput.showAndWait();
            TextField input1 = textInput.getEditor();
            if(input1.getText()!=null && input1.getText().length()!=0){
                format= input1.getText();
            }
            textInput.getEditor().clear();
            library.addNewItem(title, format);
            listView.getItems().add(title);
            });

        delete.setOnAction(e -> {
            String title="";
            tf.appendText("Which item (select below)?  ");
            title=listView.getSelectionModel().getSelectedItem();
            if(title==null) title=listView.getSelectionModel().getSelectedItem();
            if(!library.doesTitleExist(title)) {
                alertBox.display("Error","I'm sorry, I couldn't find "+title +" in the library.");
            } else {
                library.delete(title);
                listView.getItems().remove(title);
            }
        });
        checkIn.setOnAction(e->{
            String title="";
            tf.appendText("Which item (select below)?  ");
            title=listView.getSelectionModel().getSelectedItem();
            if(title==null) title=listView.getSelectionModel().getSelectedItem();

            if(!library.doesTitleExist(title)) {
                alertBox.display("Error","I'm sorry, I couldn't find "+title +" in the library.");
            } else {
                textInput.setContentText("Who are you loaning it to?");
                Optional<String> result1 = textInput.showAndWait();
                TextField input1 = textInput.getEditor();
                String name = input1.getText();
                textInput.getEditor().clear();
                String date;
                textInput.setContentText("When did you loan it to them?");
                Optional<String> result2 = textInput.showAndWait();
                TextField input2 = textInput.getEditor();
                date = input2.getText();
                textInput.getEditor().clear();
                if(!library.markItemOnLoan(title, name, date)){
                    alertBox.display("Error","I couldn't find "+title +" in the library. Or it is on loan");
                }
            }
        });
        checkOut.setOnAction(e->{
            String title;
            tf.appendText("Which item (select below)?  ");
            title=listView.getSelectionModel().getSelectedItem();
            if(title==null) title=listView.getSelectionModel().getSelectedItem();
            textInput.getEditor().clear();
            if(!library.markItemReturned(title)){
                alertBox.display("Error",title +" is currently not on loan");
            }
        });


        root.setMargin(tf,new Insets(10,10,10,0));
        root.add(tf,0,1,3,1);
        root.addRow(2,add,delete,checkIn,checkOut);

        root.add(listView,0,3,3,1);

        Scene scene=new Scene(root,400,200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lending Library");
        primaryStage.show();
    }
}


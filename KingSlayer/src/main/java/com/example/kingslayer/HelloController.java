package com.example.kingslayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import static java.util.Collections.swap;

public class HelloController {
    Stage stage; Parent root; Scene scene;
    @FXML
    void newGame(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("battleField.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
    @FXML
    void back(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
    @FXML
    void ins(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ins.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

    }
    @FXML
    void ixt(MouseEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    static class spot{
        boolean sla, king;
    }
    static spot[] point;
     static {
        point= new spot[14];
        for(int i=1; i<=13; i++){
            point[i]= new spot();
            point[i].sla=false;
            point[i].king=false;
            if(i==9)
                point[i].king=true;
            if(i<8)
                point[i].sla=true;
        }
    }
    spot[] points=point;
    @FXML
    ImageView A, B, C, D, E, F, G, H, I, J, K, L, M;
    @FXML
    Label turn;
    int cnt=0, a=0, b=0, same, kingPos=9, flag=0, left=7;
    String aValue, bValue;
    ImageView kingNode=null, slaNode=null, fakaNode=null;
    @FXML
    void move(MouseEvent event){
        cnt++;
        if(cnt==1) {
            a = (int) ((ImageView) event.getSource()).getId().charAt(0) - 64;
            same=a;
            if(points[a].king){
                aValue="k";
                kingNode=(ImageView) event.getSource();
            }else if(points[a].sla){
                aValue="s";
                slaNode=(ImageView) event.getSource();
            }else{
                aValue="f";
                fakaNode=(ImageView) event.getSource();
            }
        }
        if(cnt==2) {
            b = (int) ((ImageView) event.getSource()).getId().charAt(0)-64;
            if(b==same) { cnt=1;}
            if(cnt==2) {
                if (points[b].king) {
                    bValue = "k";
                    kingNode = (ImageView) event.getSource();
                } else if (points[b].sla) {
                    bValue = "s";
                    slaNode = (ImageView) event.getSource();
                } else {
                    bValue = "f";
                    fakaNode = (ImageView) event.getSource();
                }
                whichTwo(a, b);
                if(!isSafe(kingPos)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("GAME OVER!");
                    alert.setHeaderText("Soldiers win!");
                    alert.setContentText("The King has no place to move.");
                    alert.showAndWait();
                }
                cnt = 0;
                a = 0;
                b = 0;
                aValue = "";
                bValue = "";
//            kingNode=null; slaNode=null; fakaNode=null;
            }
        }
    }
    void whichTwo(int a, int b){
        if((aValue=="s" && bValue=="s") || (aValue=="f" && bValue=="f")){
            wrongMove();
        }else if((aValue=="s" && bValue=="f") || (aValue=="f" && bValue=="s")){
            SlaKingMove(a, b);
        }else if(aValue=="k" && bValue=="f" || aValue=="f" && bValue=="k"){
            SlaKingMove(a, b);
        }else if(aValue=="k" && bValue=="s" || aValue=="s" && bValue=="k"){
            kingsKhai(a, b);
        }
    }
    void SlaKingMove(int a, int b){
        switch (a){
            case 1: { if(b==2 || b==4) exchange(); break;}
            case 2: { if(b==1 || b==3 || b==5) exchange(); break;}
            case 3: { if(b==2 || b==6) exchange(); break;}
            case 4: { if(b==1 || b==5 || b==7) exchange(); break;}
            case 5: { if(b==2 || b==4 || b==6 || b==7) exchange(); break;}
            case 6: { if(b==3 || b==5 || b==7) exchange(); break;}
            case 7: { if(b==4 || b==5 || b==6 || b==8 || b==9 || b==10) exchange(); break;}
            case 8: { if(b==7 || b==9 || b==11) exchange(); break;}
            case 9: { if(b==7 || b==8 || b==10 || b==12) exchange(); break;}
            case 10: { if(b==7 || b==9 || b==13 )exchange(); break;}
            case 11: { if(b==8 || b==12) exchange(); break;}
            case 12: { if(b==9 || b==11 || b==13) exchange(); break;}
            case 13: { if(b==10 || b==12) exchange(); break;}
        }
        if(flag==1){
            turn.setText("King's Move...");
        }else {
            turn.setText("Soldier's Move...");
        }
    }
    void kingsKhai(int a, int b){
        int k, s;
        if(points[a].king) { k = a; s=b;}
        else { k = b; s = a;}
        switch(k){
            case 1:{
                if(s==2){
                    if(C.getImage()==null) khai(k, s, 3, C);
                    else wrongMove();
                } else if(s==4){
                    if(G.getImage()==null) khai(k, s, 7, G);
                    else wrongMove();
                } else wrongMove();
                break;
            }case 2:{
                if(s==5){
                    if(G.getImage()==null) khai(k, s, 7, G);
                    else wrongMove();
                } else wrongMove();
                break;
            }case 3:{
                if(s==2){
                    if(A.getImage()==null) khai(k, s, 1, A);
                    else wrongMove();
                } else if(s==6){
                    if(G.getImage()==null) khai(k, s, 7, G);
                    else wrongMove();
                } else  wrongMove();
                break;
            }case 4:{
                if(s==5){
                    if(F.getImage()==null) khai(k, s, 6, F);
                    else wrongMove();
                } else if(s==7){
                    if(J.getImage()==null) khai(k, s, 10, J);
                    else wrongMove();
                } else  wrongMove();
                break;
            }case 5:{
                if(s==7){
                    if(I.getImage()==null) khai(k, s, 9, I);
                    else wrongMove();
                } else wrongMove();
                break;
            }case 6:{
                if(s==5){
                    if(D.getImage()==null) khai(k, s, 4, D);
                    else wrongMove();
                } else if(s==7){
                    if(H.getImage()==null) khai(k, s, 8, H);
                    else wrongMove();
                } else wrongMove();
                break;
            }case 7:{
                if(s==4){
                    if(A.getImage()==null) khai(k, s, 1, A);
                    else wrongMove();
                } else if(s==5){
                    if(B.getImage()==null) khai(k, s, 2, B);
                    else wrongMove();
                }else if(s==6){
                    if(C.getImage()==null) khai(k, s, 3, C);
                    else wrongMove();
                }else if(s==8){
                    if(K.getImage()==null) khai(k, s, 11, K);
                    else wrongMove();
                }else if(s==9){
                    if(L.getImage()==null) khai(k, s, 12, L);
                    else wrongMove();
                }else if(s==10){
                    if(M.getImage()==null) khai(k, s, 13, M);
                    else wrongMove();
                } else wrongMove();
                break;
            }case 8:{
                if(s==7){
                    if(F.getImage()==null) khai(k, s, 6, F);
                    else wrongMove();
                } else if(s==9){
                    if(J.getImage()==null) khai(k, s, 10, J);
                    else wrongMove();
                } else wrongMove();
                break;
            }case 9:{
                if(s==7){
                    if(E.getImage()==null) khai(k, s, 5, E);
                    else wrongMove();
                } else wrongMove();
                break;
            }case 10:{
                if(s==7){
                    if(D.getImage()==null) khai(k, s, 4, D);
                    else wrongMove();
                } else if(s==9){
                    if(H.getImage()==null) khai(k, s, 8, H);
                    else wrongMove();
                } else wrongMove();
                break;
            }case 11:{
                if(s==8){
                    if(G.getImage()==null) khai(k, s, 7, G);
                    else wrongMove();
                } else if(s==12){
                    if(M.getImage()==null) khai(k, s, 13, M);
                    else wrongMove();
                } else wrongMove();
                break;
            }case 12:{
                if(s==9){
                    if(G.getImage()==null) khai(k, s, 7, G);
                    else wrongMove();
                } else wrongMove();
                break;
            }case 13:{
                if(s==10){
                    if(G.getImage()==null) khai(k, s, 7, G);
                    else wrongMove();
                } else if(s==11){
                    if(K.getImage()==null) khai(k, s, 11, K);
                    else wrongMove();
                } else wrongMove();
                break;
            }
        }
    }
    void wrongMove(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wrong Move!!");
        alert.setContentText("There might be a mistake... Check again and good luck!");
        alert.show();    }
    void exchange(){
        if(aValue=="s" || bValue=="s"){
            if(aValue=="s") {points[a].sla=false; points[b].sla=true;}
            else {points[b].sla=false; points[a].sla=true;}
            fakaNode.setImage(slaNode.getImage());
            slaNode.setImage(null);
        }
        if(aValue=="k" || bValue=="k"){
            if(aValue=="k") { kingPos=b; points[a].king=false; points[b].king=true;}
            else { kingPos= a; points[b].king=false; points[a].king=true;}
            fakaNode.setImage(kingNode.getImage());
            kingNode.setImage(null);
        }
        flag^=1;
    }
    boolean isSafe(int kingPos){
        switch (kingPos){
            case 1:{
                if(!points[2].sla || !points[3].sla || !points[4].sla || !points[7].sla){
                    return true;
                } break;
            }
            case 2:{
                if(!points[1].sla || !points[3].sla || !points[5].sla || !points[7].sla) {
                    return true;
                } break;
            }
            case 3: {
                if(!points[1].sla || !points[2].sla || !points[6].sla || !points[7].sla) {
                    return true;
                } break;
            }
            case 4: {
                if(!points[1].sla || !points[5].sla || !points[6].sla || !points[7].sla || !points[10].sla) {
                    return true;
                } break;
            }
            case 5: {
                if(!points[2].sla || !points[4].sla || !points[6].sla || !points[7].sla || !points[9].sla) {
                    return true;
                } break;
            }
            case 6: {
                if(!points[3].sla || !points[4].sla || !points[5].sla || !points[7].sla || !points[8].sla) {
                    return true;
                } break;
            }
            case 7: {
                if(!points[1].sla  || !points[2].sla || !points[3].sla || !points[4].sla || !points[5].sla || !points[6].sla || !points[8].sla || !points[9].sla || !points[10].sla || !points[11].sla || !points[12].sla || !points[13].sla) {
                    return true;
                } break;
            }
            case 8: {
                if(!points[6].sla || !points[7].sla || !points[9].sla || !points[10].sla || !points[11].sla) {
                    return true;
                } break;
            }
            case 9: {
                if(!points[5].sla || !points[7].sla || !points[8].sla || !points[10].sla || !points[12].sla) {
                    return true;
                } break;
            }
            case 10: {
                if(!points[4].sla || !points[7].sla || !points[8].sla || !points[9].sla || !points[13].sla) {
                    return true;
                } break;
            }
            case 11: {
                if(!points[7].sla || !points[8].sla || !points[12].sla || !points[13].sla) {
                    return true;
                } break;
            }
            case 12: {
                if(!points[7].sla || !points[9].sla || !points[11].sla || !points[13].sla) {
                    return true;
                } break;
            }
            case 13: {
                if(!points[7].sla || !points[10].sla || !points[11].sla || !points[12].sla) {
                    return true;
                } break;
            }
        }
        return false;
    }
    void khai(int k, int s, int f, ImageView jump){
        jump.setImage(kingNode.getImage());
        slaNode.setImage(null);
        kingNode.setImage(null);
        points[k].king=false;
        points[s].sla=false;
        points[f].king=true;
        left--;
        if(left<4){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("GAME OVER!");
            alert.setHeaderText("King wins!");
            alert.setContentText("Not enough soldiers to confine the king.");
            alert.showAndWait();
        }
        flag^=1;
    }
}
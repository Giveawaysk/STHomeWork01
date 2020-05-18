import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Test {
    public static void main(String[] args) {
        StudentSys stuSys=new StudentSys("学生管理系统");
        stuSys.initWin();
    }
}
class StudentSys extends JFrame{

    private JPanel p1,p2,p3,combop;
    private JTabbedPane tab;
    private Container container;
    private JButton b1,b2;
    private Listener listener;
    private Label nameLabel;
    private Label idLabel;
    private Label showLabel;
    private JTextField textName;
    private JTextField textId;
    private TextArea showIdArea;
    /*
     * 查找
     * */
    private Label searchLabel;
    private JTextField searchText;
    private JButton sBut;
    private JTextField resultText;
    private String[] name;
    private String[] id;

    /*
     * 排序
     * */
    private TextArea showTextArea;
    private JButton sortBut;
    private int countNum=0;
    private JButton clearBut;
    public StudentSys(String str){
        super(str);

        this.name=new String[100];
        this.id=new String[100];
        listener = new Listener();
        tab = new JTabbedPane(JTabbedPane.TOP);
        //容器
        container = this.getLayeredPane();
        //对象化面板
        combop = new JPanel();
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();

        b1 =new JButton("确认添加");
        b2 =new JButton("撤回添加");
        nameLabel =new Label("姓名");
        idLabel =new Label("学号");
        showLabel=new Label("当前记录为零!");
        textName =new JTextField(15);
        textId =new JTextField(15);
        showIdArea=new TextArea();

        /*
         * 查找
         * */
        searchLabel=new Label("请输入姓名：");
        searchText=new JTextField(15);
        sBut=new JButton("确认查找");
        resultText=new JTextField(15);
        /*
         * 排序
         * */
        showTextArea=new TextArea();
        sortBut=new JButton("学号排序");
        clearBut=new JButton("清空数据");
    }
    public void initWin(){
        this.setBounds(300, 300, 500, 400);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }});
        layoutWin();
        this.setVisible(true);
    }
    private void layoutWin(){

        tab.add(p1,"学号输入");
        tab.add(p2,"学号查询");
        tab.add(p3,"学号排序");
        combop.add(new JLabel("学生信息管理系统"));
        container.setLayout(new BorderLayout());
        container.add(combop,BorderLayout.NORTH);
        container.add(tab,BorderLayout.CENTER);

        Container con1=new Container();
        con1.setLayout(new FlowLayout());
        con1.add(nameLabel);
        con1.add(textName);

        con1.add(idLabel);
        con1.add(textId);
        p1.add(con1,BorderLayout.NORTH);
        p1.add(con1);
        p1.add(showIdArea);

        Container con2=new Container();
        con2.setLayout(new FlowLayout());
        con2.add(b1);
        con2.add(b2);
        con2.add(showLabel);
        p1.add(con2);
        b1.addActionListener(listener);
        b2.addActionListener(listener);
        /*
         * 查找布局
         * */

        Container con3=new Container();
        con3.setLayout(new FlowLayout());
        con3.add(searchLabel);
        con3.add(searchText);
        con3.add(sBut);
        p2.add(con3,BorderLayout.NORTH);
        sBut.addActionListener(listener);
        p2.add(resultText);
        /*
         * 排序布局
         * */
        p3.add(showTextArea);
        p3.add(sortBut);
        p3.add(clearBut);
        sortBut.addActionListener(listener);
        clearBut.addActionListener(listener);
    }
    /*
     * java内部类实现ActionListener接口
     * */
    class Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource()==b1){

                if((textName.getText().equals(""))||(textId.getText().equals(""))){
                    showLabel.setText("添加失败(姓名,学号不能有空)！");
                }
                else{
                    name[countNum]=textName.getText();
                    id[countNum]=textId.getText();
                    countNum++;
                    String area="添加成功,当前有"+countNum+"条记录";
                    showLabel.setText(area);
                    sortMess(false);
                    textName.setText("");
                    textId.setText("");
                }

            }
            if(e.getSource()==b2){
                if(countNum>0){
                    countNum--;
                    String area="撤回成功,当前有"+countNum+"条记录";
                    showLabel.setText(area);
                    sortMess(false);
                }
            }
            if(e.getSource()==sBut){
                if(!searchText.getText().equals("")){
                    searchMess(searchText.getText());
                }
            }
            if(e.getSource()==sortBut){
                sortMess(true);
            }
            if(e.getSource()==clearBut){
                if(!showTextArea.getText().equals("")){
                    showTextArea.setText("");
                }
            }
        }

        public void sortMess(boolean sign) {
            // TODO Auto-generated method stub
            if(sign){
                for(int i=0;i<countNum;i++){
                    for(int j=i+1;j<countNum;j++){
                        if(Integer.parseInt(id[i])<Integer.parseInt(id[j])){
                            String s1,s2;
                            s1=name[i];
                            s2=id[i];

                            name[i]=name[j];
                            id[i]=id[j];

                            name[j]=s1;
                            id[j]=s2;
                        }
                    }
                }
            }else{

                if(!showIdArea.getText().equals("")){
                    showIdArea.setText("");
                }
            }
            for(int i=0;i<countNum;i++){
                String content="姓名:"+name[i]+"\t"+"学号"+id[i];
                if(sign)showTextArea.append(content+"\n");
                else showIdArea.append(content+"\n");
            }
        }

        public void searchMess(String n) {
            // TODO Auto-generated method stub

            for(int i=0;i<countNum;i++){
                if(name[i].equals(n)){
                    String content="姓名:"+name[i]+","+"学号"+id[i];
                    resultText.setText(content);
                    return;
                }
            }
            resultText.setText("未找到该学生!");
        }
    }
}

import java.util.ArrayList;

public class ClassifyModelB implements ClassifyModel{
    private double[][] ai_y;
    private double[] yi_s;

    private static ArrayList<ArrayList<Integer>> loadData(String path){
        return new ArrayList<ArrayList<Integer>>();
    }

    public ClassifyModelB() {
        ai_y=new double[8][6];
        yi_s=new double[8];

        ArrayList<ArrayList<Integer>> data=loadData("");
        int[] countY=new int[8];
        int[][] countAY=new int[8][6];
        int sum=data.size();

        for(int i=0;i<sum;i++){
            ArrayList<Integer> lineData=data.get(i);
            //获取该条数据的类别
            int y=lineData.get(6);
            //获取各类别的总数
            countY[y]++;
            //获取各类别下各属性值的总数
            for(int j=0;j<6;j++){
                if(lineData.get(j)==1)
                    countAY[y][j]++;
            }
        }
        //初始化两个变量的值
        for(int i=0;i<8;i++){
            double tempCount=(double)countY[i];
            yi_s[i]=tempCount/sum;
            for(int j=0;j<6;j++){
                ai_y[i][j]=countAY[i][j]/tempCount;
            }
        }
    }

    public double[][] getAi_y() {
        return ai_y;
    }

    public void setAi_y(double[][] ai_y) {
        this.ai_y = ai_y;
    }

    public double[] getYi_s() {
        return yi_s;
    }

    public void setYi_s(double[] yi_s) {
        this.yi_s = yi_s;
    }

    //找到则返回对应的类别序号，未找到返回-1
    @Override
    public Integer classify(ArrayList<Integer> testData) {
        double[] score=new double[8];
        //初始化score
        System.arraycopy(yi_s, 0, score, 0, 8);
        //计算最终score
        for(int i=0;i<6;i++){
            if(testData.get(i)==1){
                for(int j=0;j<8;j++){
                    score[j]=score[j]*ai_y[j][i];
                }
            }
        }
        //判断score中的max
        double max=0;
        Integer res=-1;
        for(int i=0;i<8;i++){
            if(score[i]>max){
                res=i;
                max=score[i];
            }
        }
        return res;
    }
}

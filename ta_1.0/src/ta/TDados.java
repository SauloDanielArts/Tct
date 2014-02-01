/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author 
 */
public class TDados {

    //legendas das tabelas
    public String[] Colunas;// = new String []{"Palavras Encontradas","fi","Fi","fri","Fri","%","Angulo"};

    public void Colunas(String[] C) {
        Colunas = C;
    }
    
    public float[][] EX;//matrizes que guardan as propriedades dos textos
    public String[] classes;//classes dos textos
//public int NElementos;//tamanho do vetor das classes
    public int NPalavrasTotal;//numero de palavras no texto
    public int NPalavrasDif;//numero de palavras diferentes
    public String[][] Dados;//string que comtem os dados das tabelas
    public DefaultTableModel Modelo;//legendas das tabelas

    public void Ttexto(javax.swing.JTextArea AT, javax.swing.JTable TD) {
        //DefaultTableModel Modelo =new DefaultTableModel(null, Colunas);
        StringTokenizer sw;

        //texto
        if (AT.getText().trim().isEmpty() == false)//se o texto esta vazio
        {
            String Texto = Ptexto(AT.getText());
            classes = Ctexto(Texto);


            Dados = new String[NPalavrasDif][7];//tabela contendo os dados sobre o texto
            EX = new float[NPalavrasDif][6];//tabela contendo os dados sobre o texto em forma numerica

            //palavras---------------------
            for (int i = 0; i < NPalavrasDif; i++) {
                Dados[i][0] = classes[i];
            }
            //-----------------------------

            //fi---------------------
            for (int posicao = 0; posicao < NPalavrasDif; posicao++) {
                sw = new StringTokenizer(Texto);
                while (sw.hasMoreTokens()) {
                    String token = sw.nextToken();

                    if (classes[posicao].equalsIgnoreCase(token)) {
                        EX[posicao][0]++;
                    }
                }
            }

            for (int i = 0; i < NPalavrasDif; i++) {
                Dados[i][1] = String.valueOf((int) EX[i][0]);
            }
            //-----------------------------

            //--Fi---------------------------
            float j = EX[0][0];
            for (int posicao = 0; posicao < NPalavrasDif; posicao++) {
                EX[posicao][1] = j;
                if (posicao < NPalavrasDif - 1) {
                    j = j + EX[posicao + 1][0];
                }
            }

            for (int i = 0; i < NPalavrasDif; i++) {
                Dados[i][2] = String.valueOf((int) EX[i][1]);
            }
            //-------------------------------

            //--fri--------------------------
            for (int posicao = 0; posicao < NPalavrasDif; posicao++) {
                EX[posicao][2] = EX[posicao][0] / NPalavrasTotal;
            }

            for (int i = 0; i < NPalavrasDif; i++) {
                Dados[i][3] = String.valueOf(EX[i][2]);
            }
            //-------------------------------

            //--Fri--------------------------
            j = EX[0][2];
            for (int posicao = 0; posicao < NPalavrasDif; posicao++) {
                EX[posicao][3] = j;
                if (posicao < NPalavrasDif - 1) {
                    j = j + EX[posicao + 1][2];
                }
            }

            for (int i = 0; i < NPalavrasDif; i++) {
                Dados[i][4] = String.valueOf(EX[i][3]);
            }
            //-------------------------------

            //--%----------------------------
            for (int posicao = 0; posicao < NPalavrasDif; posicao++) {
                EX[posicao][4] = EX[posicao][2] * 100;
            }

            for (int i = 0; i < NPalavrasDif; i++) {
                Dados[i][5] = String.valueOf(EX[i][4]);
            }
            //-------------------------------

            //-Angulo-----------------------
            for (int posicao = 0; posicao < NPalavrasDif; posicao++) {
                EX[posicao][5] = EX[posicao][3] * 360;
            }
            for (int i = 0; i < NPalavrasDif; i++) {
                Dados[i][6] = String.valueOf(EX[i][5]);
            }
            //-------------------------------

            //for (int i = 0; i < NPalavrasDif; i++)
            //System.out.println("\n" + Dados[i][1]);

            Modelo = new DefaultTableModel(Dados, Colunas);
            TD.setModel(Modelo);
        }
    }

    public String[] Ctexto(String Texto) {
        Texto = Ptexto(Texto);//formata o testo apenas com numeros e letras
        Texto = Texto.trim();//retira o espaco em branco do comesso e fim
        Texto = Texto.toLowerCase();//todas asletras para minusculo

        boolean rep = false; //indica repeticao de palavras
        int posicao = 0; //posicao no vetor palavras


        if (Texto.isEmpty() == true)//se o texto esta vazio
        {
            return null;
        } else {

            NPalavrasTotal = 0;//numero de palavras no texto
            NPalavrasDif = 0;//numero de palavras diferentes

            //calcula o numero de palavras do texto
            StringTokenizer sw = new StringTokenizer(Texto);
            while (sw.hasMoreTokens()) {
                String token = sw.nextToken();
                NPalavrasTotal++;
            }

            String[] palavras = new String[NPalavrasTotal];//vetor que contem todas as palavras do texto

            sw = new StringTokenizer(Texto);//emcontra as palavras repetidas e as anula
            while (sw.hasMoreTokens()) {
                String token = sw.nextToken();

                for (int i = posicao; i >= 0; i--)//percore o vetor apartir da ultima posicao valida
                {
                    if (token.equals(palavras[i]))//se ouver repeticao
                    {
                        rep = true;
                        palavras[posicao] = " ";//palavra na String recebe vasio
                        break;
                    } else {
                        rep = false;
                    }
                }

                if (rep == false)//se nao olver repeticao
                {
                    palavras[posicao] = token;
                    NPalavrasDif++;//numero de palavras diferntes no texto
                }
                posicao++;
            }

             //for(int i=0;i<NPalavrasDif;i++)
            // System.out.println(palavras[i]);//palavras diferentes*/

            String[] clas = new String[NPalavrasDif];//cria um vetor com todas as palavras diferentes do texto

            posicao = 0;

            
            for (int i = 0; i < NPalavrasTotal; i++)//preenche o vetor das palavras
            {
                if (palavras[i] != " ")//se a palavra nao esta vazio
                {
                    clas[posicao] = palavras[i];//a palavra e uma classe
                    posicao++;
                }
            }

            //for(int i=0;i<NPalavrasDif;i++)//test das variaveis das classes
            //System.out.println(clas[i]+"\n");

            Arrays.sort(clas);//ordena o vetor das classes

            //for(int i=0;i<NPalavrasDif;i++)//test das variaveis das classes
            //System.out.println(clas[i]+"\n");

            return clas;
        }
    }

    public void LoadTexto(javax.swing.JTextArea AT)//carega um arquivo de testo em uma JTextArea
    {
        JFileChooser fc = new JFileChooser();//janela de selecao de arquivo

        int res = fc.showOpenDialog(null);//captura o ponteiro do arquivo

        //se o enderesso do arquivo foi inicializado com sucesso
        if (res == JFileChooser.APPROVE_OPTION) {
            File arquivo = fc.getSelectedFile();//carega o arquivo de testo
            AT.setText("");//JTextArea recebe vasio

            try {
                BufferedReader in = new BufferedReader(new FileReader(arquivo));//caeaga a string caracter por caracter em um buffer
                String str, texto = "";
                while ((str = in.readLine()) != null) {//enquanto o arquivo nao acabar
                    texto += str;
                }
                AT.setText(texto);//JTextArea recebe o texto caregado
                in.close();//finalisa o buffer
            } catch (IOException ioe) {
                // possiveis erros são tratatos
            }
        }
    }

    public String Ptexto(String Texto) {//parametrisa a String retirddo caracteres nao desejados
        for (int i = 0; i < Texto.length(); i++) {
            if (Texto.charAt(i) < 48 || Texto.charAt(i) > 57 && Texto.charAt(i) < 65 ||
                    Texto.charAt(i) > 90 && Texto.charAt(i) < 97 ||
                    Texto.charAt(i) > 122 && Texto.charAt(i) < 192 ||
                    Texto.charAt(i) > 221 && Texto.charAt(i) < 224 ||
                    Texto.charAt(i) > 246 && Texto.charAt(i) < 249 ||
                    Texto.charAt(i) > 249 && Texto.charAt(i) < 253 || Texto.charAt(i) >= 255) {
                Texto = Texto.replace(Texto.charAt(i), ' ');
            }
        }
        return Texto;
    }

     public float PerSTexto(TDados T)//retorna quantos porcento t1 e similar a t2
    {
        float per = 0, soma = 0;

        for (int i = 0; i < NPalavrasDif; i++) {
            for (int j = 0; j < T.NPalavrasDif; j++) {
                if (classes[i].equals(T.classes[j])) {
                    if (EX[i][0] > T.EX[j][0]) {
                        soma = soma + T.EX[j][0];
                    } else if (EX[i][0] < T.EX[j][0]) {
                        soma = soma + EX[i][0];
                    } else if (EX[i][0] == T.EX[j][0]) {
                        soma = soma + EX[i][0];
                    }
                }
            }
        }

        per = (soma * 100) / EX[NPalavrasDif - 1][1];//Fi

        //System.out.println(""+"("+soma*100+""+")/"+EX[NPalavrasDif-1][1]+"= "+per);

        return per;
    }


 //--grafico de pizza-----------------------------
    public void GraficoP(String Titulo, String Cab)//imprime um grafico de pizza comparando a frequencia de cada palavra
    {
    // cria a dataset...
    DefaultPieDataset data = new DefaultPieDataset();
    for (int i = 0; i < NPalavrasDif; i++) {
    data.setValue("" + classes[i], EX[i][4]);
    }

    // cria um chart...
    JFreeChart chart = ChartFactory.createPieChart3D(
    Titulo,
    data,
    false, // legend?
    true, // tooltips?
    true // URLs?
    );
    // cria uma tela para o frame...

    ChartFrame frame = new ChartFrame(Cab, chart);
    frame.setPreferredSize(new java.awt.Dimension(800, 570));
    frame.pack();
    frame.setVisible(true);
    
    }
}
//---------------------------------------------------------------------------------------------------------------------

/*public void GraficoP(float[] V)//inprime o grafico de comparacao //funcao original
{
int Tamanho=V.length;
// create a dataset...
DefaultPieDataset data = new DefaultPieDataset();
for(int i=0;i<Tamanho;i++)
data.setValue("classe"+i+"= "+V[i]+"%", V[i]);

// create a chart...
JFreeChart chart = ChartFactory.createPieChart(
"Sample Pie Chart",
data,
false, // legend?
false, // tooltips?
false // URLs?
);
// create and display a frame...
ChartFrame frame = new ChartFrame("First", chart);
frame.pack();
frame.setVisible(true);
}*/
/*public float PerSTexto(TDados T1,TDados T2)//retorna quantos porcento t1 e similar a t2
{
float per = 0, soma = 0;

for (int i = 0; i < T1.NPalavrasDif-1; i++) {
for (int j = 0; j < T2.NPalavrasDif-1; j++) {
if (T1.classes[i].equals(T2.classes[j])) {
if (T1.EX[i][0] > T2.EX[j][0]) {
soma = soma + T2.EX[j][0];
} else if (T1.EX[i][0] < T2.EX[j][0]) {
soma = soma + T1.EX[i][0];
} else if (T1.EX[i][0] == T2.EX[j][0]) {
soma = soma + T1.EX[i][0];
}
}
}
}

per = (soma * 100) / T1.EX[T1.NPalavrasDif - 1][1];//Fi

//System.out.println(""+"("+soma*100+""+")/"+EX[NPalavrasDif-1][1]+"= "+per);

return per;
}*/



 /*DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(2, "Marks", "Rahul");
        dataset.setValue(7, "Marks", "Vinod");
        dataset.setValue(4, "Marks", "Deepak");
        dataset.setValue(9, "Marks", "Prashant");
        dataset.setValue(6, "Marks", "Chandan");
        JFreeChart chart = ChartFactory.createBarChart("BarChart using JFreeChart","Student", "Marks", dataset, PlotOrientation.VERTICAL, false,true, false);
        chart.setBackgroundPaint(Color.yellow);
        chart.getTitle().setPaint(Color.blue);
        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.red);
        ChartFrame frame1=new ChartFrame("Bar Chart",chart);
        frame1.setVisible(true);
        frame1.setSize(400,350);*/




 /*CombinedCategoryPlotDemo1 demo = new CombinedCategoryPlotDemo1("Combined Category Plot Demo 1");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);*/
/*
 public class CombinedCategoryPlotDemo1 extends ApplicationFrame {

       
        public CombinedCategoryPlotDemo1(String title) {
            super(title);
            JPanel chartPanel = createDemoPanel();
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
            setContentPane(chartPanel);
        }

        public CategoryDataset createDataset1() {
            DefaultCategoryDataset result = new DefaultCategoryDataset();
            String series1 = "First";
            String series2 = "Second";
            String type1 = "Type 1";
            String type2 = "Type 2";
            String type3 = "Type 3";
            String type4 = "Type 4";
            String type5 = "Type 5";
            String type6 = "Type 6";
            String type7 = "Type 7";
            String type8 = "Type 8";

            result.addValue(1.0, series1, type1);
            result.addValue(4.0, series1, type2);
            result.addValue(3.0, series1, type3);
            result.addValue(5.0, series1, type4);
            result.addValue(5.0, series1, type5);
            result.addValue(7.0, series1, type6);
            result.addValue(7.0, series1, type7);
            result.addValue(8.0, series1, type8);

            result.addValue(5.0, series2, type1);
            result.addValue(7.0, series2, type2);
            result.addValue(6.0, series2, type3);
            result.addValue(8.0, series2, type4);
            result.addValue(4.0, series2, type5);
            result.addValue(4.0, series2, type6);
            result.addValue(2.0, series2, type7);
            result.addValue(1.0, series2, type8);

            return result;
        }

        public CategoryDataset createDataset2() {

        DefaultCategoryDataset result = new DefaultCategoryDataset();

        String series1 = "Third";
        String series2 = "Fourth";

        String type1 = "Type 1";
        String type2 = "Type 2";
        String type3 = "Type 3";
        String type4 = "Type 4";
        String type5 = "Type 5";
        String type6 = "Type 6";
        String type7 = "Type 7";
        String type8 = "Type 8";

        result.addValue(11.0, series1, type1);
        result.addValue(14.0, series1, type2);
        result.addValue(13.0, series1, type3);
        result.addValue(15.0, series1, type4);
        result.addValue(15.0, series1, type5);
        result.addValue(17.0, series1, type6);
        result.addValue(17.0, series1, type7);
        result.addValue(18.0, series1, type8);

        result.addValue(15.0, series2, type1);
        result.addValue(17.0, series2, type2);
        result.addValue(16.0, series2, type3);
        result.addValue(18.0, series2, type4);
        result.addValue(14.0, series2, type5);
        result.addValue(14.0, series2, type6);
        result.addValue(12.0, series2, type7);
        result.addValue(11.0, series2, type8);

        return result;

        }
        private JFreeChart createChart() {

            CategoryDataset dataset1 = createDataset1();
            NumberAxis rangeAxis1 = new NumberAxis("Value");
            rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
            renderer1.setBaseToolTipGenerator(
                    new StandardCategoryToolTipGenerator());
            CategoryPlot subplot1 = new CategoryPlot(dataset1, null, rangeAxis1,
                    renderer1);
            subplot1.setDomainGridlinesVisible(true);

            CategoryDataset dataset2 = createDataset2();
            NumberAxis rangeAxis2 = new NumberAxis("Value");
            rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            BarRenderer renderer2 = new BarRenderer();
            renderer2.setBaseToolTipGenerator(
            new StandardCategoryToolTipGenerator());
            CategoryPlot subplot2 = new CategoryPlot(dataset2, null, rangeAxis2,
            renderer2);
            subplot2.setDomainGridlinesVisible(true);

            CategoryAxis domainAxis = new CategoryAxis("Category");
            CombinedCategoryPlot plot = new CombinedCategoryPlot(
                    domainAxis, new NumberAxis("Range"));
            plot.add(subplot1, 2);
            plot.add(subplot2, 1);

            JFreeChart result = new JFreeChart(
                    "Combined Domain Category Plot Demo",
                    new Font("SansSerif", Font.BOLD, 12), plot, true);
            return result;

        }



        public JPanel createDemoPanel() {
            JFreeChart chart = createChart();
            return new ChartPanel(chart);
        }
    } */

/*public class Histograma {

    private JFreeChart grafico;
    private int[][] frequencia = new int[256][3];


    public Histograma() {
    this.grafico = this.criaChart();
    }//fim do construtor

    public JFreeChart getChart() {
    return this.grafico;
    }

    private JFreeChart criaChart() {

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    //insere os valores no dataset para carregar no grafico
    for (int x = 0; x < this.frequencia.length; x++) {
    String v2 = String.valueOf(x);
    // String v2 = x == 0 || 16 % x == 0? String.valueOf(x) : "";
    dataset.addValue(frequencia[x][0], "R", v2);
    dataset.addValue(frequencia[x][1], "G", v2);
    dataset.addValue(frequencia[x][2], "B", v2);
    }

    JFreeChart chart = ChartFactory.createLineChart(
    "Histograma", // chart title
    "Pixels", // domain axis label
    "Quantidade", // range axis label
    dataset, // data
    PlotOrientation.VERTICAL, // orientation
    true, // include legend
    true, // tooltips?
    false // URLs?
    );
    return chart;
    }//fim do criaChart

    }//fim da classe*/



 /*Histogram histogramdemo1 = new Histogram("Histogram Demo 1");
        histogramdemo1.pack();
        RefineryUtilities.centerFrameOnScreen(histogramdemo1);
        histogramdemo1.setVisible(true);*/
 /*public class Histogram extends ApplicationFrame
    {

    public Histogram(String s)
    {
    super(s);
    IntervalXYDataset intervalxydataset = createDataset();
    JFreeChart jfreechart = createChart(intervalxydataset);
    ChartPanel chartpanel = new ChartPanel(jfreechart);
    chartpanel.setPreferredSize(new Dimension(500, 270));
    setContentPane(chartpanel);
    }


    private IntervalXYDataset createDataset()
    {
    HistogramDataset histogramdataset = new HistogramDataset();
    double ad[] = {
    0.0D, 2D, 3D, 4D, 5D, 6D, 7D, 8D, 9D, 10D
    };
    double ad2[] = {
    1.0D, 1D, 1D, 2D, 2D, 3D, 3D, 4D, 4D, 1D
    };
    histogramdataset.addSeries("H1", ad, 10, 0.0, 10);
    histogramdataset.addSeries("H2", ad2, 10, 0.0, 10);
    return histogramdataset;
    }

    private JFreeChart createChart(IntervalXYDataset intervalxydataset)
    {
    JFreeChart jfreechart = ChartFactory.createHistogram("Histogram Demo", "fsdfsdf", "ewerwer", intervalxydataset, PlotOrientation.VERTICAL, false, true, true);
    jfreechart.getXYPlot().setForegroundAlpha(0.6F);
    return jfreechart;
    }
    }*/

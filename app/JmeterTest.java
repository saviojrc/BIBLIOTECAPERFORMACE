import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import java.io.File;
import java.io.IOException;

public class JmeterTest {

    public static void main(String[] args) throws IOException {

        // Define o caminho do arquivo .jmx
        String jmxFile = "C:\\Users\\savio\\CODIGO\\BIBLIOTECAPERFORMACE\\app\\resources\\BIBLIOTECA.jmx";
        // Define o caminho do diretório de saída dos relatórios
        String outputDir = "C:\\Users\\savio\\CODIGO\\BIBLIOTECAPERFORMACE\\app\\resources\\reports\\";

        // Inicializa o motor do Jmeter
        StandardJMeterEngine jmeter = new StandardJMeterEngine();

        // Configura as propriedades do Jmeter
        JMeterUtils.loadJMeterProperties("C:\\Users\\savio\\CODIGO\\BIBLIOTECAPERFORMACE\\app\\resources\\files\\jmeter.properties");
        JMeterUtils.setJMeterHome("C:\\Users\\savio\\jmeter");
        JMeterUtils.initLocale();
        // Carrega o arquivo .jmx
        HashTree testPlanTree = SaveService.loadTree(new File(jmxFile));

        // Define um resumo do teste
        Summariser summariser = new Summariser("Sumário Performa Biblioteca");

        // Define um coletor de resultados que salva os dados em um arquivo .csv
        String csvFile = outputDir + "resultados.csv";
        ResultCollector csvCollector = new ResultCollector(summariser);

        csvCollector.setFilename(csvFile);
        testPlanTree.add(testPlanTree.getArray()[0], csvCollector);

        // Define um coletor de resultados que gera um relatório em HTML
        String htmlFile = outputDir + "relatório\\";
        ResultCollector htmlCollector = new ResultCollector(summariser);
        htmlCollector.setFilename(htmlFile);
        testPlanTree.add(testPlanTree.getArray()[0], htmlCollector);

        // Executa o teste
        jmeter.configure(testPlanTree);
        jmeter.run();

        // Finaliza o motor do Jmeter
        jmeter.exit();

        System.out.println("Teste concluído. Verifique os relatórios em " + outputDir);

    }
}

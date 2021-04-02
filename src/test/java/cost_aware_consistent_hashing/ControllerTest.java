package cost_aware_consistent_hashing;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ControllerTest {
    private Controller controller = new Controller();
    
    @Test
    public void uniformTest() throws InterruptedException{
        ExperimentResults experimentResults = controller.runExperiment(DataSetType.UNIFORM, AlgorithmType.MODULO);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void normalTest() throws InterruptedException{
        ExperimentResults experimentResults = controller.runExperiment(DataSetType.NORMAL, AlgorithmType.MODULO);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void normalConsitentTest() throws InterruptedException{
        ExperimentResults experimentResults = controller.runExperiment(DataSetType.NORMAL, AlgorithmType.CONSISTENT);
        assertTrue(experimentResults.getTotalTime() > 0);
    }

    @Test
    public void zipfTest() throws InterruptedException{
        ExperimentResults experimentResults = controller.runExperiment(DataSetType.ZIPF, AlgorithmType.MODULO);
        assertTrue(experimentResults.getTotalTime() > 0);
    }
}

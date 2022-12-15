package piece_tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({PieceTest.class,
            BombTest.class,
            FlagTest.class,
            MinerTest.class,
            ScoutTest.class,
            SpyTest.class,
            StepMoverTest.class
            })
public class PieceTestSuite {  // nothing
}

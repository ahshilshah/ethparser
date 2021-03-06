package pro.belbix.ethparser.web3;

import static org.junit.Assert.*;
import static pro.belbix.ethparser.web3.contracts.Tokens.BAS_NAME;
import static pro.belbix.ethparser.web3.contracts.LpContracts.UNI_LP_WBTC_BADGER;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pro.belbix.ethparser.Application;
import pro.belbix.ethparser.web3.prices.PriceProvider;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class PriceProviderTest {

    @Autowired
    private PriceProvider priceProvider;

    @Before
    public void setUp() throws Exception {
        priceProvider.setUpdateBlockDifference(1);
    }

    @Test
    public void getLpPositionAmountInUsdWithNonNullBlockTest() {
        double amountUsd =
            priceProvider.getLpPositionAmountInUsd(UNI_LP_WBTC_BADGER, 0.00000630081174343, 11387098L);
        assertEquals("437,96", String.format("%.2f", amountUsd));
    }

    @Test
    public void priceForBAS() {
        double price = priceProvider.getPriceForCoin(BAS_NAME, 11619379L);
        assertEquals("143,06", String.format("%.2f", price));
    }
}

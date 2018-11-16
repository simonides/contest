import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class mainTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void main() {
        assertThat(4, is(2+2));
    }
}
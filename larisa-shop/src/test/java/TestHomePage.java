import com.bochkov.WicketApplication;
import com.bochkov.shop.page.HomePage;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	@Test
	public void homepageRendersSuccessfully()
	{
		//start and render the test backPage
		tester.startPage(HomePage.class);

		//assert rendered backPage class
		tester.assertRenderedPage(HomePage.class);
	}
}

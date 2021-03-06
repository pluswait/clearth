/******************************************************************************
 * Copyright 2009-2020 Exactpro Systems Limited
 * https://www.exactpro.com
 * Build Software to Test Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.exactprosystems.clearth.automation;

import com.exactprosystems.clearth.ApplicationManager;
import com.exactprosystems.clearth.ClearThCore;
import com.exactprosystems.clearth.automation.exceptions.AutomationException;
import com.exactprosystems.clearth.utils.ClearThException;
import org.junit.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static com.exactprosystems.clearth.ApplicationManager.USER_DIR;
import static com.exactprosystems.clearth.ApplicationManager.waitForSchedulerToStop;
import static org.junit.Assert.*;

public class TestInvertedResults
{
	private static final Path TEST_CONFIG_DIR = USER_DIR.resolve("src/test/resources/InvertedResults");
	private static final Path MATRICES_DIR = TEST_CONFIG_DIR.resolve("matrices");
	private static final Path CONFIGS_DIR = TEST_CONFIG_DIR.resolve("configs");
	private static final Path CONFIG = CONFIGS_DIR.resolve("config.cfg");

	private final String userName = "test", schedulerName = "Test";

	private static ApplicationManager clearThManager;

	@Test
	public void testInvertedResults() throws ClearThException, AutomationException, IOException
	{
		Scheduler scheduler = clearThManager.getScheduler(schedulerName, userName);
		scheduler.clearSteps();
		clearThManager.loadSteps(scheduler, CONFIG.toFile());
		clearThManager.loadMatrices(scheduler, MATRICES_DIR.toFile());
		
		scheduler.start(userName);
		waitForSchedulerToStop(scheduler, 100, 10000);
		
		List<Step> steps = scheduler.getSteps();
		if(steps == null || steps.isEmpty())
			fail("There is gotta be at least one step in Scheduler configuration due to the test case.");

		Step step = steps.get(0);

		Assert.assertTrue(step.isAnyActionFailed());
		Assert.assertFalse(step.isFailedDueToError());
		Assert.assertFalse(scheduler.isSuccessful());

	}

	@BeforeClass
	public static void startTestApp() throws ClearThException
	{
		clearThManager = new ApplicationManager();
	}

	@After
	public void clearSchedulerData()
	{
		SchedulersManager manager = ClearThCore.getInstance().getSchedulersManager();
		List<Scheduler> userSchedulers = manager.getUserSchedulers(userName);
		userSchedulers.clear();
	}

	@AfterClass
	public static void disposeTestApp() throws IOException
	{
		if (clearThManager != null) clearThManager.dispose();
	}

}

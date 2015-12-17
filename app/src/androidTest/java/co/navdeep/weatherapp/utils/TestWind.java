/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.navdeep.weatherapp.utils;

import android.test.AndroidTestCase;

public class TestWind extends AndroidTestCase {

    public static final String LOG_TAG = TestWind.class.getSimpleName();

    public void testConvertDegreesToDirectionString() {
//        {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        assertEquals("N", Wind.convertDegreesToDirectionString(0));
        assertEquals("N", Wind.convertDegreesToDirectionString(360));
        assertEquals("N", Wind.convertDegreesToDirectionString(22));

        assertEquals("NE", Wind.convertDegreesToDirectionString(45));
        assertEquals("NE", Wind.convertDegreesToDirectionString(30));
        assertEquals("NE", Wind.convertDegreesToDirectionString(60));

        assertEquals("E", Wind.convertDegreesToDirectionString(90));
        assertEquals("E", Wind.convertDegreesToDirectionString(110));
        assertEquals("E", Wind.convertDegreesToDirectionString(70));

        assertEquals("SE", Wind.convertDegreesToDirectionString(120));
        assertEquals("SE", Wind.convertDegreesToDirectionString(150));
        assertEquals("SE", Wind.convertDegreesToDirectionString(135));

        assertEquals("S", Wind.convertDegreesToDirectionString(160));
        assertEquals("S", Wind.convertDegreesToDirectionString(180));
        assertEquals("S", Wind.convertDegreesToDirectionString(200));

        assertEquals("SW", Wind.convertDegreesToDirectionString(210));
        assertEquals("SW", Wind.convertDegreesToDirectionString(225));
        assertEquals("SW", Wind.convertDegreesToDirectionString(240));

        assertEquals("W", Wind.convertDegreesToDirectionString(270));
        assertEquals("W", Wind.convertDegreesToDirectionString(290));
        assertEquals("W", Wind.convertDegreesToDirectionString(250));

        assertEquals("NW", Wind.convertDegreesToDirectionString(315));
        assertEquals("NW", Wind.convertDegreesToDirectionString(300));
        assertEquals("NW", Wind.convertDegreesToDirectionString(330));

    }
}

package model;

import static org.junit.Assert.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayTogetherStateTest {
    private UserManager userManager;
    private CommunityManager communityManager;
    private SessionManager sessionManager;
    private CourtFacilityManager facilityManager;
    private PlayTogetherState testPlayState;

    @BeforeEach
    public void runBefore() {
        userManager = new UserManager();
        communityManager = new CommunityManager();
        sessionManager = new SessionManager();
        facilityManager = new CourtFacilityManager();
        testPlayState = new PlayTogetherState(userManager, communityManager, sessionManager, facilityManager);
    }

    @Test
    public void constructorTest() {
        assertEquals(userManager, testPlayState.getUserManager());
        assertEquals(communityManager, testPlayState.getCommunityManager());
        assertEquals(sessionManager, testPlayState.getSessionManager());
        assertEquals(facilityManager, testPlayState.getFacilityManager());
    }

}

//package za.co.wethinkcode.robotworld.server;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import za.co.wethinkcode.robotworld.interfaces.IRobot;
//import za.co.wethinkcode.robotworld.interfaces.IWorld;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class ForwardCommandTest {
//
//    private IRobot mockRobot;
//    private IWorld mockWorld;
//    private ForwardCommand forwardCommand;
//
//    @BeforeEach
//    void setUp() {
//        mockRobot = mock(IRobot.class);
//        mockWorld = mock(IWorld.class);
//        when(mockRobot.getWorld()).thenReturn(mockWorld);
//        forwardCommand = new ForwardCommand("3");
//    }
//
//    @Test
//    void testExecuteSuccessfulMove() {
//        when(mockWorld.updatePosition(anyInt())).thenReturn(IWorld.UpdateResponse.SUCCESS);
//
//        CommandResponse response = forwardCommand.execute(mockRobot);
//
//        verify(mockRobot, times(3)).getWorld();
//        verify(mockWorld, times(3)).updatePosition(1);
//        verify(mockRobot).setStatus("Moved forward by 3 steps.");
//        assertEquals("forward 3", response.getCommand().getName());
//    }
//
//    @Test
//    void testExecuteObstructedMove() {
//        when(mockWorld.updatePosition(anyInt())).thenReturn(IWorld.UpdateResponse.FAILED_OBSTRUCTED);
//
//        CommandResponse response = forwardCommand.execute(mockRobot);
//
//        verify(mockRobot, atLeast(1)).getWorld();
//        verify(mockWorld, atLeast(1)).updatePosition(1);
//        verify(mockRobot).setStatus("Obstructed");
//        assertEquals("forward 3", response.getCommand().getName());
//    }
//
//    @Test
//    void testExecuteObstructedByRobotMove() {
//        when(mockWorld.updatePosition(anyInt())).thenReturn(IWorld.UpdateResponse.FAILED_OBSTRUCTED_ROBOT);
//
//        CommandResponse response = forwardCommand.execute(mockRobot);
//
//        verify(mockRobot, atLeast(1)).getWorld();
//        verify(mockWorld, atLeast(1)).updatePosition(1);
//        verify(mockRobot).setStatus("Obstructed by a bot");
//        assertEquals("forward 3", response.getCommand().getName());
//    }
//
//    @Test
//    void testExecuteOutsideSafeZone() {
//        when(mockWorld.updatePosition(anyInt())).thenReturn(IWorld.UpdateResponse.FAILED_OUTSIDE_SAFE_ZONE);
//
//        CommandResponse response = forwardCommand.execute(mockRobot);
//
//        verify(mockRobot, atLeast(1)).getWorld();
//        verify(mockWorld, atLeast(1)).updatePosition(1);
//        verify(mockRobot).setStatus("Sorry, I cannot go outside my safe zone.");
//        assertEquals("forward 3", response.getCommand().getName());
//    }
//}


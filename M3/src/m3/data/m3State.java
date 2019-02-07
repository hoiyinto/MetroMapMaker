package m3.data;

/**
 * This enum has the various possible states of the logo maker app
 * during the editing process which helps us determine which controls
 * are usable or not and what specific user actions should affect.
 * 
 * @author Richard McKenna
 * @author Sabrina To
 * @version 1.0
 */
public enum m3State {
    ADDING_STATION,
    REMOVING_STATION,
    SELECTING,
    DRAGGING_STATION,
    DRAGGING_LINE,
    DRAGGING_LABEL,
    DRAGGING_IMAGE,
    SIZING_LINE,  
}

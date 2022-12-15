package view;

public class EventCloser {
   private boolean isGuiOpen;
   private boolean isCLIOpen;

   /**
    * Sets the CLI to close state
    */
   public void setCliClose() {
      this.isCLIOpen = false;
   }

   /**
    * Sets the GUI to close state
    */
   public void setGuiClose() {
      this.isGuiOpen = false;
   }

   public EventCloser() {
      isCLIOpen = true;
      isGuiOpen = true;
   }

   /**
    * THis method is used to close the application when both GUI and CLI closes/
    */
   public void closeApplication() {
      if (!isCLIOpen && !isGuiOpen) {
         System.exit(0);
      }
   }
}

import UserNotifications

@objc(Notification)
class Notification: NSObject {

    @objc(multiply:withB:withResolver:withRejecter:)
    func multiply(a: Float, b: Float, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
        resolve(a*b)
    }

    @objc(showNotification:text:triggerTime:)
    func showNotification(title: String, text: String, triggerTime:Float) -> Void {
        if #available(iOS 10.0, *) {
            UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .badge, .sound]) { success, error in
                if success {
                    print("All set!")
                    if #available(iOS 10.0, *) {
                        let content = UNMutableNotificationContent()
                        content.title = title
                        content.subtitle = text
                        content.sound = UNNotificationSound.default

                        // show this notification five seconds from now
                        let trigger = UNTimeIntervalNotificationTrigger(timeInterval: TimeInterval((triggerTime/1000)), repeats: false)

                        // choose a random identifier
                        let request = UNNotificationRequest(identifier: UUID().uuidString, content: content, trigger: trigger)

                        // add our notification request
                        UNUserNotificationCenter.current().add(request)
                    } else {
                        // Fallback on earlier versions
                    }

                } else if let error = error {
                    print(error.localizedDescription)
                }
            }
        } else {
            // Fallback on earlier versions
        }
    }
}

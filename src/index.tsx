import { NativeModules, Platform, DeviceEventEmitter } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-notification' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const Notification = NativeModules.Notification
  ? NativeModules.Notification
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function multiply(a: number, b: number): Promise<number> {
  return Notification.multiply(a, b);
}

export function showNotification(
  text: string,
  title: string,
  triggerTime: number
): any {
  return Notification.showNotification(text, title, triggerTime);
}

export function registerTestEvent(callback: any) {
  DeviceEventEmitter.addListener('onTestingEvents', callback);
}

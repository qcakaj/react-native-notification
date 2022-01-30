import * as React from 'react';

import { StyleSheet, View, Button, TextInput, Text } from 'react-native';
import {
  multiply,
  showNotification,
  registerTestEvent,
} from 'react-native-notification';
export default function App() {
  const [result, setResult] = React.useState<number | undefined>();
  const [title, onChangeTitle] = React.useState<string>();
  const [text, onChangeText] = React.useState<string>();
  const [triggerTime, onTriggerTimeChanged] = React.useState<string>();

  const [counter, setCounter] = React.useState(0);
  const [startCountdown, setStartCountdown] = React.useState(false);
  React.useEffect(() => {
    registerTestEvent(({testing_events}: any) => {
      console.log('notific clicked', testing_events);
    });
  },[]);
  React.useEffect(() => {
    if (startCountdown) {
      const timer =
        counter > 0 && setInterval(() => setCounter(counter - 1), 1000);

      if (counter === 0) {
        // countdown is finished
        setStartCountdown(false);
        // update your redux state here
        // updateReduxCounter(0);
      }

      return () => clearInterval(timer);
    }
  }, [counter, startCountdown]);

  React.useEffect(() => {
    multiply(3, 7).then(setResult);
  }, []);

  return (
    <View style={styles.container}>
      <View
        style={{ width: '100%', paddingHorizontal: '12%', marginVertical: 2 }}
      >
        <Text style={{ alignSelf: 'flex-start' }}>Countdown: {counter}</Text>
      </View>
      <View
        style={{ width: '100%', paddingHorizontal: '12%', marginVertical: 12 }}
      >
        <TextInput
          style={{ borderWidth: 1, marginBottom: 12 }}
          value={title}
          onChangeText={onChangeTitle}
          placeholder="Notification title"
          keyboardType="default"
        />
        <TextInput
          style={{ borderWidth: 1, marginBottom: 12 }}
          value={text}
          onChangeText={onChangeText}
          placeholder="Notification body"
          keyboardType="default"
        />

        <TextInput
          style={{ borderWidth: 1, marginBottom: 18 }}
          value={triggerTime}
          onChangeText={onTriggerTimeChanged}
          placeholder="Trigger time (millis)"
          keyboardType="numeric"
        />

        <Button
          onPress={() => {
            setCounter(Number(triggerTime) / 1000);
            setStartCountdown(true);
            console.log('trigger time', triggerTime);
            console.log('numberTriggerTime', Number(triggerTime));
            showNotification(title, text, Number(triggerTime));
          }}
          title="Show"
          color="#841584"
          accessibilityLabel="Learn more about this purple button"
        />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});

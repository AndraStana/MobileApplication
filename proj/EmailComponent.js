import React, { Component} from 'react';
import{
    View,
    Linking,
    TextInput,
    Button,
} from 'react-native'



export class EmailComponent extends Component{

    static navigationOption = {
        header:null,
    }

    render(){
        return(

            <View>
                <TextInput onChangeText={(email)=>this.setState({email})} />
                <TextInput onChangeText={(content)=>this.setState({content}) }/>
                <Button
                    onPress={() => {
                        subject = "Email sent from React Native";
                        all = "mailto:" + this.state.email + "?subject=" + subject + "&body=" + this.state.content ;
                        Linking.openURL(all)}}
                    title="Send Email"
                    color="#841584"
                />
            </View>

        )
    }
}

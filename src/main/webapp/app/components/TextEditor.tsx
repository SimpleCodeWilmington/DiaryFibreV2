import React, { Component } from 'react'
import { Editor } from "react-draft-wysiwyg";
import { EditorState } from 'draft-js';

import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";

export class TextEditor extends Component {



    state = {
      editorState: EditorState.createEmpty(),
    }

  
    onEditorStateChange = (editorState: any) => {
      this.setState({

        editorState,

      });
    };

  render() {
      const { editorState } = this.state;

    return (
      <div>textEditor

        <Editor
          editorState={editorState}
          toolbarClassName="toolbarClassName"
          wrapperClassName="wrapperClassName"
          editorClassName="editorClassName"
          onEditorStateChange={this.onEditorStateChange}
        />

      </div>
    );
  }
}

export default TextEditor;
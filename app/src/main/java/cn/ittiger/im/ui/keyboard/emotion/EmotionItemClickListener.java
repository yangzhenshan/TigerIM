package cn.ittiger.im.ui.keyboard.emotion;

import cn.ittiger.im.adapter.EmotionAdapter;
import cn.ittiger.im.constant.EmotionType;
import cn.ittiger.im.ui.recyclerview.CommonRecyclerView;
import cn.ittiger.im.ui.recyclerview.HeaderAndFooterAdapter;
import cn.ittiger.im.util.EmotionUtil;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

/**
 * @author: laohu on 2017/2/6
 * @site: http://ittiger.cn
 */
public class EmotionItemClickListener implements CommonRecyclerView.OnItemClickListener {
    private Context mContext;
    private EditText mEditText;
    private EmotionType mEmotionType;

    public EmotionItemClickListener(Context context, EditText editText, EmotionType emotionType) {

        mContext = context;
        mEditText = editText;
        mEmotionType = emotionType;
    }

    @Override
    public void onItemClick(HeaderAndFooterAdapter adapter, int position, View itemView) {

        if(!(adapter instanceof EmotionAdapter)) {
            return;
        }
        if(position == adapter.getItemDataCount() - 1) {//点击了最后一个，即删除表情按钮
            //发送一个删除事件交给系统进行删除操作
            mEditText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        } else {//点击了表情，则添加到输入框
            EmotionAdapter emotionAdapter = (EmotionAdapter) adapter;
            String emotionName = emotionAdapter.getItem(position);//得到点击表情的名称

            // 获取输入框当前光标位置,在指定位置上添加表情图片文本
            int curPosition = mEditText.getSelectionStart();
            StringBuilder sb = new StringBuilder(mEditText.getText().toString());
            sb.insert(curPosition, emotionName);

            // 特殊文字处理,将表情等转换一下
            mEditText.setText(EmotionUtil.getInputEmotionContent(mContext, mEmotionType, mEditText, sb.toString()));

            // 将光标设置到新增完表情的右侧
            mEditText.setSelection(curPosition + emotionName.length());
        }
    }


}
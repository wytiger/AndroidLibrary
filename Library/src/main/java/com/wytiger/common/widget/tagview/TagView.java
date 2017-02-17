package com.wytiger.common.widget.tagview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.wytiger.lib.R;

public class TagView extends RelativeLayout {

    private List<Tag> mTags = new ArrayList<Tag>();
    private ViewTreeObserver mViewTreeObserber;

    private OnTagClickListener mClickListener;
    private OnTagDeleteListener mDeleteListener;
    private int mWidth;
    private boolean mInitialized = false;

    int lineMargin;
    int tagMargin;
    int textPaddingLeft;
    int textPaddingRight;
    int textPaddingTop;
    int texPaddingBottom;

    public TagView(Context ctx) {
        super(ctx, null);
        initialize(ctx, null, 0);
    }

    public TagView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        initialize(ctx, attrs, 0);
    }

    public TagView(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
        initialize(ctx, attrs, defStyle);
    }

    private void initialize(Context ctx, AttributeSet attrs, int defStyle) {
        mViewTreeObserber = getViewTreeObserver();
        mViewTreeObserber.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!mInitialized) {
                    mInitialized = true;
                    drawTags();
                }
            }
        });

        // get AttributeSet
        TypedArray typeArray = ctx.obtainStyledAttributes(attrs, R.styleable.TagView, defStyle, defStyle);
        this.lineMargin = (int) typeArray.getDimension(R.styleable.TagView_lineMargin, DensityUtils.dip2px(Constants.DEFAULT_LINE_MARGIN));
        this.tagMargin = (int) typeArray.getDimension(R.styleable.TagView_tagMargin, DensityUtils.dip2px(Constants.DEFAULT_TAG_MARGIN));
        this.textPaddingLeft = (int) typeArray.getDimension(R.styleable.TagView_textPaddingLeft, DensityUtils.dip2px(Constants.DEFAULT_TAG_TEXT_PADDING_LEFT));
        this.textPaddingRight = (int) typeArray.getDimension(R.styleable.TagView_textPaddingRight, DensityUtils.dip2px(Constants.DEFAULT_TAG_TEXT_PADDING_RIGHT));
        this.textPaddingTop = (int) typeArray.getDimension(R.styleable.TagView_textPaddingTop, DensityUtils.dip2px(Constants.DEFAULT_TAG_TEXT_PADDING_TOP));
        this.texPaddingBottom = (int) typeArray.getDimension(R.styleable.TagView_textPaddingBottom, DensityUtils.dip2px(Constants.DEFAULT_TAG_TEXT_PADDING_BOTTOM));
        typeArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        if (width <= 0) return;
        mWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTags();
    }

    private void drawTags() {
        if (!mInitialized) {
            return;
        }
        removeAllViews();

        float total = getPaddingLeft() + getPaddingRight();

        int listIndex = 1;// List Index
        int index_bottom = 1;// The Tag to add below
        int index_header = 1;// The header tag of this line
        Tag tag_pre = null;
        for (Tag item : mTags) {
            final int position = listIndex - 1;
            final Tag tag = item;
            View tagLayout = View.inflate(getContext(), R.layout.tagview_item, null);
            tagLayout.setId(listIndex);
            tagLayout.setBackgroundDrawable(getSelector(tag));

            // tag text
            TextView tagView = (TextView) tagLayout.findViewById(R.id.tv_tag_item_contain);
            tagView.setText(tag.text);
            //tagView.setPadding(textPaddingLeft, textPaddingTop, textPaddingRight, texPaddingBottom);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tagView.getLayoutParams();
            params.setMargins(textPaddingLeft, textPaddingTop, textPaddingRight, texPaddingBottom);
            tagView.setLayoutParams(params);
            tagView.setTextColor(tag.tagTextColor);
            tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tag.tagTextSize);
            tagLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onTagClick(tag, position);
                    }
                }
            });

            // calculate　of tag layout width
            float tagWidth = tagView.getPaint().measureText(tag.text) + textPaddingLeft + textPaddingRight;
            // tagView padding (left & right)

            // deletable text
            TextView deletableView = (TextView) tagLayout.findViewById(R.id.tv_tag_item_delete);
            if (tag.isDeletable) {
                deletableView.setVisibility(View.VISIBLE);
                deletableView.setText(tag.deleteIcon);
                int offset = DensityUtils.dip2px(2f);
                deletableView.setPadding(offset, textPaddingTop, textPaddingRight + offset, texPaddingBottom);
                /*params = (LinearLayout.LayoutParams) deletableView.getLayoutParams();
                params.setMargins(offset, textPaddingTop, textPaddingRight+offset, texPaddingBottom);
				deletableView.setLayoutParams(params);*/
                deletableView.setTextColor(tag.deleteIndicatorColor);
                deletableView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tag.deleteIndicatorSize);
                deletableView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TagView.this.remove(position);
                        if (mDeleteListener != null) {
                            mDeleteListener.onTagDeleted(tag, position);
                        }
                    }
                });
                tagWidth += deletableView.getPaint().measureText(tag.deleteIcon) + textPaddingLeft + textPaddingRight;
                // deletableView Padding (left & right)
            } else {
                deletableView.setVisibility(View.GONE);
            }

            LayoutParams tagParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //tagParams.setMargins(0, 0, 0, 0);

            //add margin of each line
            tagParams.bottomMargin = lineMargin;

            if (mWidth <= total + tagWidth + DensityUtils.dip2px(Constants.LAYOUT_WIDTH_OFFSET)) {
                //need to add in new line
                tagParams.addRule(RelativeLayout.BELOW, index_bottom);
                // initialize total param (layout padding left & layout padding right)
                total = getPaddingLeft() + getPaddingRight();
                index_bottom = listIndex;
                index_header = listIndex;
            } else {
                //no need to new line
                tagParams.addRule(RelativeLayout.ALIGN_TOP, index_header);
                //not header of the line
                if (listIndex != index_header) {
                    tagParams.addRule(RelativeLayout.RIGHT_OF, listIndex - 1);
                    tagParams.leftMargin = tagMargin;
                    total += tagMargin;
                    if (tag_pre != null && tag_pre.tagTextSize < tag.tagTextSize) {
                        index_bottom = listIndex;
                    }
                }
            }
            total += tagWidth;
            addView(tagLayout, tagParams);
            tag_pre = tag;
            listIndex++;
        }
    }

    private Drawable getSelector(Tag tag) {
        if (tag.background != null) {
            return tag.background;
        }
        StateListDrawable states = new StateListDrawable();
        GradientDrawable gd_normal = new GradientDrawable();
        gd_normal.setColor(tag.layoutColor);
        gd_normal.setCornerRadius(tag.radius);
        if (tag.layoutBorderSize > 0) {
            gd_normal.setStroke(DensityUtils.dip2px(tag.layoutBorderSize), tag.layoutBorderColor);
        }
        GradientDrawable gd_press = new GradientDrawable();
        gd_press.setColor(tag.layoutColorPress);
        gd_press.setCornerRadius(tag.radius);
        states.addState(new int[]{android.R.attr.state_pressed}, gd_press);
        states.addState(new int[]{}, gd_normal);
        return states;
    }

    public void addTag(Tag tag) {
        mTags.add(tag);
        drawTags();
    }

    public List<Tag> getTags() {
        return mTags;
    }

    public void setTags(List<Tag> tags) {
        mTags.clear();
        mTags.addAll(tags);
        drawTags();
    }

    public void remove(int position) {
        mTags.remove(position);
        drawTags();
    }

    public void removeAllTags() {
        mTags.clear();
        drawTags();
    }

    public int getLineMargin() {
        return lineMargin;
    }

    public void setLineMargin(float lineMargin) {
        this.lineMargin = DensityUtils.dip2px(lineMargin);
    }

    public int getTagMargin() {
        return tagMargin;
    }

    public void setTagMargin(float tagMargin) {
        this.tagMargin = DensityUtils.dip2px(tagMargin);
    }

    public int getTextPaddingLeft() {
        return textPaddingLeft;
    }

    public void setTextPaddingLeft(float textPaddingLeft) {
        this.textPaddingLeft = DensityUtils.dip2px(textPaddingLeft);
    }

    public int getTextPaddingRight() {
        return textPaddingRight;
    }

    public void setTextPaddingRight(float textPaddingRight) {
        this.textPaddingRight = DensityUtils.dip2px(textPaddingRight);
    }

    public int getTextPaddingTop() {
        return textPaddingTop;
    }

    public void setTextPaddingTop(float textPaddingTop) {
        this.textPaddingTop = DensityUtils.dip2px(textPaddingTop);
    }

    public int getTexPaddingBottom() {
        return texPaddingBottom;
    }

    public void setTexPaddingBottom(float texPaddingBottom) {
        this.texPaddingBottom = DensityUtils.dip2px(texPaddingBottom);
    }

    public void setOnTagClickListener(OnTagClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void setOnTagDeleteListener(OnTagDeleteListener deleteListener) {
        mDeleteListener = deleteListener;
    }
}

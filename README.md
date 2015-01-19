# SlidingLayout
A library for sliding views, which is usually used in book readers.

一个针对电子书阅读的划屏库 libsliding。

[Demo下载](https://github.com/xuzb/SlidingLayout/blob/master/app-debug.apk?raw=true)

## Overview
本库的主要相关的类如下：

SlidingLayout: 用于滑动的Layout，类似ViewPager

SldingAdapter: 用于获取数据和页面展示的类，类似Android的Adapter，不过SlidingAdapter有自己的接口需要实现

Slider: 滑动方式的基类，包括PageSlider和OverlappedSlider，分别表示左右平移滑动和左右覆盖滑动。

## Usage
在布局文件里加入如下的xml
```xml
<com.martian.libsliding.SlidingLayout
    android:id="@+id/sliding_container"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

在Java代码里获取到SlidlingLayout的实例后（设为slidingLayout），
首先设置提供内容的Adapter，Adapter需要继承SlidingAdapter实现相应的接口
```java
slidingLayout.setAdapter(new SlidingAdapter() {...}); // 基于SlidingAdapter创建Adapter实例
```

然后设置滑动方式
```java
slidingLayout.setSlider(new PageSlider()); // 左右平移滑动，类似ViewPager
```
或者
```java
slidingLayout.setSlider(new OverlappedSlider()); // 左右覆盖滑动
```

具体用法可参照目录app下的MainActivity.java

## SlidingAdapter
由于电子书内容的获取一般是顺序的获取，因此不同于Android的BaseAdapter，
SlidingAdapter提供了关于next和previous的接口，具体如下：
```java
// 通过内容实例t来创建或更新contentView，
// 若contentView为null，则创建新的view，否则更新contentView
// 返回为创建或更新后的contentView
public abstract View getView(View contentView, T t);

// 获取当前要显示的内容实例
public abstract T getCurrent();

// 获取下一个要显示的内容实例
public abstract T getNext();

// 获取前一个要显示的内容实例
public abstract T getPrevious();

// 判断当前是否还有下一个内容实例
public abstract boolean hasNext();

// 判断当前是否还有前一个内容实例
public abstract boolean hasPrevious();

// 实现如何从当前的实例移动到下一个实例
protected abstract void computeNext();

// 实现如何从当前的实例移动到前一个实例
protected abstract void computePrevious();
```

该库已使用在我们的小说阅读应用[淘小说](http://zhushou.360.cn/detail/index/soft_id/2369773?recrefer=SE_D_%E5%B0%8F%E8%AF%B4)中，欢迎大家下载试用

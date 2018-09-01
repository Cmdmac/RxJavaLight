# RxJavaLight
一个简易的RxJava库

example:
```
Observable.create(new ObservableOnSubscribe<String>() {
    @Override
    public void subscribe(ObservableEmitter<String> emitter) {
        Log.e("test", Thread.currentThread().getName()  + " test");
        emitter.onNext(" test");
        emitter.onComplete();
    }
}).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.mainThread())
  .map(new Func<String, String>() {
      @Override
      public String apply(String from) {
          return from + "-------";
      }
  })
  .observeOn(Schedulers.mainThread())
  .doOnComplete(new Action() {
      @Override
      public void run() {
          Log.e("test", Thread.currentThread().getName()  + " doOnComplete");
      }
  })
  .observeOn(Schedulers.mainThread())
  .subscribe(new Consumer<String>() {
      @Override
      public void accept(String data) {
          Log.e("test", Thread.currentThread().getName()  + " test");
      }
});
```
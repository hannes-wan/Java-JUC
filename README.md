# JUC
- [JUC](#juc)
  - [Lock](#lock)
    - [虚假判断](#虚假判断)
    - [八锁现象](#八锁现象)
  - [集合不安全](#集合不安全)
    - [List不安全](#list不安全)
    - [Set不安全](#set不安全)
    - [Map不安全](#map不安全)
  - [Callable](#callable)
  - [常用的辅助类](#常用的辅助类)
    - [CountDownLatch](#countdownlatch)
    - [CyclicBarrier](#cyclicbarrier)
    - [Semaphore](#semaphore)
  - [ReadWriteLcok](#readwritelcok)
  - [阻塞队列](#阻塞队列)
    - [阻塞队列的四组API](#阻塞队列的四组api)
  - [SynchronousQueue 同步队列](#synchronousqueue同步队列)
  - [线程池](#线程池)
  - [四大函数式接口](#四大函数式接口)
  - [Stream流式计算](#stream流式计算)
    - [ForkJoin](#forkjoin)
  - [异步回调](#异步回调)
  - [JMM](#jmm)
  - [Unsafe类](#unsafe类)
  - [指令重排](#指令重排)
  - [单例模式](#单例模式)
  - [CAS](#cas)
  - [各种锁的理解](#各种锁的理解)

## Lock
- 可重入锁（ReentrantLock）（最常用）
    - 公平锁（FairSync）
        - 先来后到
    - 非公平锁（NonfairSync）（默认是非公平锁）
        - 可以插队

- 读锁（ReentrantReadWriteLock.ReadLock）

- 写锁（RenntrantReadWriteLcok.WriteLock）

### 虚假判断
> 如果wait()方法不是在循环里而是在if里
> 就会出现虚假判断问题
```java
if (number == 0) {
    // 等待操作
    wait();
}
```
> 应该改成
```java
while (number == 0) {
    // 等待操作
    wait();
}
```

### 八锁现象
> 深刻理解锁
> [https://blog.csdn.net/qq_31748587/article/details/105498566](https://blog.csdn.net/qq_31748587/article/details/105498566)


## 集合不安全
### List不安全
```java
/**
 * 解决方案：
 *      (1) 用Vector替代
 *      List<String> list = new Vector<>();
 *
 *      (2) 用Collections.synchronizedList替代
 *      List<String> list = Collections.synchronizedList(new ArrayList<>);
 *
 *      (3) 用CopyOnWriteArrayList
 *      List<String> list = new CopyOnWriteArrayList<>();
 *      原理：
 *          写入时复制
 *          多个线程调用时，List读取的时候是固定的
 *          写入的时候，避免覆盖造成数据问题
 *
 * CopyOnWriteArrayList比Vector牛逼在哪里：
 *      Vector用的是synchronized
 *      CopyOnWriteArrayList用的是ReentrantLock
 */
```

### Set不安全
```java
/**
 * 解决方案：
 *      (1) Collections.synchronizedSet(new HashSet<>())
 *      Set<String> set = Collections.synchronizedSet(new HashSet<>());
 *      (2) CopyOnWriteArraySet<>()
 *      Set<String> set = new CopyOnWriteArraySet<>();
 */
```

### Map不安全
```java
/**
 * HashMap的线程也是不安全的
 * HashMap在工作中不会直接new HashMap<>()
 * 需要加上加载因子和初始化容量
 * 默认加载因子0.75f
 * 默认初始化容量：16
 * 所以new HashMap() == new HashMap(16, 0.75)
 */

/**
 * 解决方案：
 *      (1) Hashtable
 *      Map<String, String> hashtable = new Hashtable<>();
 *      (2) Collections.synchronizedMap
 *      Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
 *      (3) ConcurrentHashMap
 *      Map<String, String> map = new ConcurrentHashMap<>();
 */
```

## Callable
> 可以有返回值
> 可以抛出异常
> 方法不同 call()

## 常用的辅助类
### CountDownLatch
> 减法计数器
>> countDownLatch这个类使一个线程等待其他线程各自执行完毕后再执行。
>> 是通过一个计数器来实现的，计数器的初始值是线程的数量。每当一个线程执行完毕后，计数器的值就-1，当计数器的值为0时，表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了

### CyclicBarrier
> 加法计数器

### Semaphore
> 信号量
>> 限流

## ReadWriteLcok
> 读可以被多个线程同时读
> 写只有一个线程可以写

## 阻塞队列
> FIFO
>> 写入：如果队列满了，就必须阻塞等待
>> 取出：如果队列是空的，必须阻塞等待生产
>>> 所有已知实现类：
>>> ArrayBlockingQueue
>>> DelayQueue
>>> LinkedBlockingDeque
>>> LinkedBlockingQueue
>>> LinkedTransferQueue
>>> PriorityBlockingQueue
>>> SynchronousQueue

> 什么情况下我们会使用阻塞队列
>> 多线程：线程池

### 阻塞队列的四组API
| 方式 | 抛出异常 | 有返回值 | 阻塞等待 | 超时等待 |
|------------|---------|---------|----------|----------|
| 添加 | add(value) | offer(value) | put(value) | offer(value, time, TimeUnit.SECONDS) |
| 移除 | remove() | pool() | take() | poll(time, TimeUnit.SECONDS) |
| 判断队列首 | element() | peek()  |          |          |

## SynchronousQueue 同步队列
> 没有容量
> 进去一个元素，必须等待取出来之后，才能往里放一个元素
> put添加、take删除

## 线程池
> 池化技术
>> 程序运行的本质：占用系统的资源，所以需要优化资源的使用
- 线程池的好处
    - 降低资源的消耗、提高响应速度
    - 方便管理
    - 线程复用
    - 控制最大并发数

> 线程池考点
>> 三大方法、七大参数、四种拒绝策略

- 线程池参数详解
    1. corePoolSize：线程池中的常驻核心线程数
        - 在创建了线程池后，当有请求任务来之后，就会安排池中的线程去执行请求任务，近似理解为今日当值线程
        - 当线程池中的线程数目达到 corePoolSize 后，就会把到达的任务放到缓存队列当中；
    2. maximumPoolSize：线程池能够容纳同时执行的最大线程数，此值必须大于等于1
    3. keepAliveTime：多余的空闲线程的存活时间。
        - 当前线程池数量超过 corePoolSize 时，当空闲时间达到 keepAliveTime 值时，多余空闲线程会被销毁直到只剩下 corePoolSize 个线程为止
        - 默认情况下，只有当线程池中的线程数大于 corePoolSize 时 keepAliveTime 才会起作用，直到线程池中的线程数不大于 corePoolSize
    4. unit：keepAliveTime 的单位
    5. workQueue：任务队列，被提交但尚未被执行的任务。
    6. threadFactory：表示生成线程池中工作线程的线程工厂，用于创建线程一般用默认的即可。
    7. handler：拒绝策略，表示当队列满了并且工作线程大于等于线程池的最大线程数（maximumPoolSize）时如何来报道
```java
/**
 *
 * 定义线程的数量：
 * CPU密集：
 *      定义：
 *          几核的CPU就是几，可以保持CPU效率最高
 *          用Runtime.getRuntime().availableProcessors()可以直到CPU核心数
 * I/O密集：
 *      定义：
 *          IO密集型指的是系统的CPU性能相对硬盘、内存要好很多
 *          此时系统运作，大部分的状况是CPU在等I/O (硬盘/内存) 的读/写操作，此时CPU Loading并不高
 *          判断程序中十分耗I/O的线程，让这个数量 * 2
 */
```

```java
/**
 * 拒绝策略：
 *      // 如果池和等待队列都满了，还有线程进来，不处理，直接抛出异常
 *      new ThreadPoolExecutor.AbortPolicy());
 *      // 哪来的去哪里执行
 *      new ThreadPoolExecutor.CallerRunsPolicy());
 *      // 队列满了直接丢掉任务，也不抛出异常
 *      new ThreadPoolExecutor.DiscardPolicy());
 *      // 队列满了，尝试去和最早的线程竞争，失败了就不执行，也不抛出异常
 *      new ThreadPoolExecutor.DiscardOldestPolicy());
 */
ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,                      // 核心的池大小
                5,                      // 最大的池大小
                3,                      // 从最大池里面取出的空间，如果三秒内不被使用，就会关闭这个空间
                TimeUnit.SECONDS,       // 时间单位
                new LinkedBlockingDeque<>(3),               // 等待队列，最多有3个线程在等待进入池
                Executors.defaultThreadFactory(),           // 默认的线程工厂（一般不去动它）
                new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略
```

## 四大函数式接口
> 新时代程序员必备
>> Lambda表达式
>> 链式编程
>> 函数式接口
>> Stream流式计算

- 函数式接口类型
    - 消费型（consumer）
        - 输入泛型，无返回
    - 提供型（supplier）
        - 无输入，返回泛型
    - 函数型（function）
        - 输入泛型，输出泛型
    - 断言型（predicate）
        - 输入泛型，输出boolean

## Stream流式计算
- 流的生成
    1. Collection接口的stream()或parallelStream()方法
    2. 静态的Stream.of()、Stream.empty()方法
    3. Arrays.stream(array, from, to)
    4. 静态的Stream.generate()方法生成无限流，接受一个不包含引元的函数
    5. 静态的Stream.iterate()方法生成无限流，接受一个种子值以及一个迭代函数
    6. Pattern接口的splitAsStream(input)方法
    7. 静态的Files.lines(path)、Files.lines(path, charSet)方法
    8. 静态的Stream.concat()方法将两个流连接起来

- 流的中间操作
    - count()
        - 返回流中元素个数
    - filter(predicate) 
        - 将结果为false的元素过滤掉
    - map(function) 
        - 转换元素的值，可以用方法引元或者lambda表达式
    - flatMap(function) 
        - 若元素是流，将流摊平为正常元素，再进行元素转换
    - limit(number) 
        - 保留前n个元素
    - skip(number) 
        - 跳过前n个元素
    - distinct() 
        - 剔除重复元素
    - sorted() 
        - 将Comparable元素的流排序
    - sorted(Comparator) 
        - 将流元素按Comparator排序
    - peek(consumer) 
        - 流不变，但会把每个元素传入Consumer执行，没有返回值
        - 和map对比，map传入function，有返回值，peek传入consumer，没有返回值

- 流的终结操作
    - 约简操作
        - max(Comparator)
        - min(Comparator)
        - count()
        - findFirst() 
            - 返回第一个元素
        - findAny() 
            - 返回任意元素
        - anyMatch(predicate) 
            - 任意元素匹配时返回true
        - allMatch(predicate) 
            - 所有元素匹配时返回true
        - noneMatch(predicate) 
            - 没有元素匹配时返回true
        - reduce(function) 
            - 从流中计算某个值，接受一个二元函数作为累积器，从前两个元素开始持续应用它，累积器的中间结果作为第一个参数，流元素作为第二个参数
        - reduce(a, function) 
            - a为幺元值，作为累积器的起点
        - reduce(a, function1, function2) 
            - 与二元变形类似，并发操作中，当累积器的第一个参数与第二个参数都为流元素类型时，可以对各个中间结果也应用累积器进行合并，但是当累积器的第一个参数不是流元素类型而是类型T的时候，各个中间结果也为类型T，需要fun2来将各个中间结果进行合并

    - 收集操作
        - iterator()
        - forEach(function)
        - forEachOrdered(function) 
            - 可以应用在并行流上以保持元素顺序
        - toArray()
        - toArray(T[] :: new) 
            - 返回正确的元素类型
        - collect(Collector)
        - collect(function1, function2, function3) 
            - fun1转换流元素；fun2为累积器，将fun1的转换结果累积起来；fun3为组合器，将并行处理过程中累积器的各个结果组合起来

    - Collector收集器
        - Collectors.toList()
        - Collectors.toSet()
        - Collectors.toCollection(集合的构造器引用)
        - Collectors.joining()、Collectors.joining(delimiter)、Collectors.joining(delimiter、prefix、suffix) 
            - 字符串元素连接
        - Collectors.summarizingInt/Long/Double(ToInt/Long/DoubleFunction) 
            - 产生Int/Long/DoubleSummaryStatistics对象，它有getCount、getSum、getMax、getMin方法，注意在没有元素时，getMax和getMin返回Integer/Long/Double.MAX/MIN_VALUE
        - Collectors.toMap(fun1, fun2)/toConcurrentMap 
            - 两个fun用来产生键和值，若值为元素本身，则fun2为Function.identity()
        - Collectors.toMap(fun1, fun2, fun3)/toConcurrentMap 
            - fun3用于解决键冲突，例如(oldValue, newValue) -> oldValue，有冲突时保留原值
        - Collectors.toMap(fun1, fun2, fun3, fun4)/toConcurrentMap 
            - 默认返回HashMap或ConcurrentHashMap，fun4可以指定返回的Map类型，为对应的构造器引元
        - Collectors.groupingBy(fun)/groupingByConcurrent(fun) 
            - fun是分类函数，生成Map，键是fun函数结果，值是具有相同fun函数结果元素的列表
        - Collectors.partitioningBy(fun) 
            - 键是true/false，当fun是断言函数时用此方法，比groupingBy(fun)更高效
        - Collectors.groupingBy(fun1, fun2) 
            - fun2为下游收集器，可以将列表转换成其他形式，例如toSet()、counting()、summingInt/Long/Double(fun)、maxBy(Comparator)、minBy(Comparator)、mapping(fun1, fun2)(fun1为转换函数，fun2为下游收集器)

```java
ArrayList<User> list = new ArrayList<>( Arrays.asList(
        new User(1, "a", 21),
        new User(2, "b", 22),
        new User(3, "c", 23),
        new User(4, "d", 24),
        new User(6, "e", 25)));

// 计算使用Stream流
// 链式编程、lambda表达式、函数式接口、Stream流式计算
list.stream()
        .filter((u) -> {return u.getId()%2==0;})
        .filter((u) -> {return u.getAge()>23;})
        .map((u) -> {return u.getName().toUpperCase();})
        .sorted(Comparator.naturalOrder())
        .limit(1)
        .forEach(System.out::println);
}
```

### ForkJoin
> ForkJoin用来并行执行任务，提高效率，适用于大数据量

> ForkJoin特点
>> 工作窃取

```java
/**
 * 求和计算任务
 * 使用ForkJoin步骤：
 *      1. 通过ForkJoinPool执行
 *      2. extends RecursiveAction或者RecursiveTask
 *      2. 计算任务 forkJoinPool.execute(ForkJoinTask task)，实现compute()计算接口
 *
 * ForkJoinTask子类：
 *      CounterComplete
 *      RecursiveAction     递归事件，没有返回值
 *      RecursiveTask       递归任务，有返回值
 */
```

## 异步回调
- Future设计初衷：对将来的某个事件的结果进行建模

## JMM
- Volatile
    - Java虚拟机提供的轻量级同步机制
    - 保证**可见性**
    - **不保证原子性**
    - **避免指令重排**

- JMM
    - Java内存模型，不存在的东西，是一种约定

- JMM的同步约定
    - 线程解锁前，必须把共享变量立刻刷回主存
    - 线程加锁前，必须读取主存中的最新值到工作内存中
    - 加锁和解锁是同一把锁

- JMM的八种操作
    - lock（锁定）：作用于主内存的变量，它把一个变量标识为一条线程独占的状态
    - unlock（解锁）：作用于主内存的变量，它把一个处于锁定状态的变量释放出来，释放后的变量 才可以被其他线程锁定
    - read（读取）：作用于主内存的变量，它把一个变量的值从主内存传输到线程的工作内存中，以 便随后的load动作使用
    - load（载入）：作用于工作内存的变量，它把read操作从主内存中得到的变量值放入工作内存的 变量副本中
    - use（使用）：作用于工作内存的变量，它把工作内存中一个变量的值传递给执行引擎，每当虚拟机遇到一个需要使用变量的值的字节码指令时将会执行这个操作
    - assign（赋值）：作用于工作内存的变量，它把一个从执行引擎接收的值赋给工作内存的变量，每当虚拟机遇到一个给变量赋值的字节码指令时执行这个操作
    - store（存储）：作用于工作内存的变量，它把工作内存中一个变量的值传送到主内存中，以便随 后的write操作使用
    - write（写入）：作用于主内存的变量，它把store操作从工作内存中得到的变量的值放入主内存的变量中

- 内存交互规则
    - 不允许read和load、store和write操作之一单独出现，即不允许一个变量从主内存读取了但工作内存不接受，或者工作内存发起回写了但主内存不接受的情况出现
    - 不允许一个线程丢弃它最近的assign操作，即变量在工作内存中改变了之后必须把该变化同步回 主内存
    - 不允许一个线程无原因地（没有发生过任何assign操作）把数据从线程的工作内存同步回主内存 中
    - 一个新的变量只能在主内存中“诞生”，不允许在工作内存中直接使用一个未被初始化（load或 assign）的变量，换句话说就是对一个变量实施use、store操作之前，必须先执行assign和load操作
    - 一个变量在同一个时刻只允许一条线程对其进行lock操作，但lock操作可以被同一条线程重复执 行多次，多次执行lock后，只有执行相同次数的unlock操作，变量才会被解锁
    - 如果对一个变量执行lock操作，那将会清空工作内存中此变量的值，在执行引擎使用这个变量前，需要重新执行load或assign操作以初始化变量的值
    - 如果一个变量事先没有被lock操作锁定，那就不允许对它执行unlock操作，也不允许去unlock一个 被其他线程锁定的变量
    - 对一个变量执行unlock操作之前，必须先把此变量同步回主内存中（执行store、write操作）

## Unsafe类
- 不安全
- 但是类的底层直接和操作系统挂钩，在内存中修改值
- Unsafe类是一个很特殊的存在

## 指令重排
- 什么是指令重排
    - 你写的程序，计算机并不是按照你写的那样去执行
    - 源代码 -> 编译器优化的重排 -> 指令并行也可能会重排 -> 内存也会重排 -> 执行
    - 处理器在进行指令重排的时候，考虑：数据间的依赖性

```C
int x = 1;  // 1
int y = 2;  // 2
x = x * 5;  // 3
y = x + y;  // 4

// 我们期望按顺序1234执行，但是具体实施可能是2134或者1324，但不可能是4123
```

> 指令重排可能造成的影响

> a b x y的默认值都是0

| **线程A** | **线程B** |
| --- | --- |
| x = a | y = b |
| b = 1 | a = 2 |

> 正常的结果：x == 0, y == 0;

> 但指令重排可能让它变成

| **线程A** | **线程B** |
| --- | --- |
| b = 1 | a = 2 |
| x = a | y = b |

> 指令重排导致的诡异结果变成：x == 2, y == 1;

- **只要加了volitile就可以避免指令重排**

## 单例模式
> 单例的目的是保证某个类仅有一个实例
> 当有某些类创建对象内存开销较大时可以考虑使用该模式
> 单例模式又分为饿汉式和懒汉式
>> 当我们调用这个方法时，如果类持有的引用不为空就返回这个引用，如果类保持的引用为空就创建该类的实例并将实例的引用赋予该类保持的引用
>> 同时我们还将该类的构造函数定义为私有方法，这样其他处的代码就无法通过调用该类的构造函数来实例化该类的对象，只有通过该类提供的静态方法来得到该类的唯一实例

- 饿汉式
    - 该模式能简单快速的创建一个单例对象，而且是线程安全的(只在类加载时才会初始化，以后都不会)
    - 不管你要不要都会直接创建一个对象，会消耗一定的性能(当然很小很小，几乎可以忽略不计，所以这种模式在很多场合十分常用而且十分简单)

- 懒汉式
    - 只在你需要对象时才会生成单例对象(比如调用getInstance方法)
    - 线程不安全
        - 使用synchronized 或者 volatile 可以解决

## CAS
> 什么是CAS
> compare and set
> 比较当前工作内存中的值和主内存中的值，如果这个值是期望的，那么执行操作，如果不是就一直循环（自旋锁）

- CAS缺点
    - 由于底层是自旋锁，循环会耗时
    - 一次性只能保证一个共享变量的原子性
    - ABA问题
        - ABA问题（狸猫换太子）
        - 左边的A不知道自己拿到的1是已经被动过的1
        - 如何解决ABA问题 -> 原子引用（带版本号的原子操作）
            - AtomicReference类

## 各种锁的理解
- 公平锁、非公平锁
    - 公平锁
        - 不能插队，先来后到
        - Lock lock = new ReentrantLock(true);
    - 非公平锁（默认）
        - 可以插队
        - Lock lock = new ReentrantLock(false);
        - Lock lock = new ReentrantLock();

- 可重入锁
    - 拿到外面的锁后，就能自动拿到里面的锁

- 自旋锁
    - CAS，以自旋锁为底层

import Image from "next/image";
import Link from "next/link";

const features = [
  {
    tag: "SEARCH",
    title: "축제·영화 스마트 검색",
    description:
      "날짜, 장소, 키워드로 전주국제영화제 상영작과 시간표를 검색하고 상영관 위치까지 한 번에 확인해요.",
  },
  {
    tag: "ANCHOR",
    title: "상영관 주변 자동 탐색",
    description:
      "선택한 영화 상영 시간과 상영관을 고정점으로, 반경 2~5km의 관광지·맛집·숙소를 추천해요.",
  },
  {
    tag: "ROUTE",
    title: "일정 자동 생성 · 편집",
    description:
      "N박 M일 일정에 맞춰 최단 동선 타임라인을 자동으로 만들고, 드래그 앤 드롭으로 자유롭게 편집해요.",
  },
  {
    tag: "FESTIVAL",
    title: "전북 축제 연계 추천",
    description:
      "같은 기간 열리는 임실·순창 등 전북 인근 축제를 여행 마지막 날 일정에 자연스럽게 이어줘요.",
  },
];

export default function Home() {
  return (
    <>
      {/* 히어로 */}
      <section className="bg-gradient-to-b from-primary-light to-cream">
        <div className="mx-auto flex max-w-6xl flex-col items-center gap-6 px-4 py-20 text-center">
          <Image
            src="/logo.png"
            alt="라도 트립 로고"
            width={160}
            height={160}
            priority
          />
          <h1 className="text-4xl font-bold leading-tight text-primary md:text-5xl">
            영화도, 여행도
            <br />
            동선 낭비 없이 전주답게
          </h1>
          <p className="max-w-xl text-lg text-ink-muted">
            전주국제영화제 상영 일정을 중심으로 관광지, 맛집, 숙소를 엮어
            나만의 최적 여행 코스를 만들어 드려요.
          </p>
          <div className="flex gap-3">
            <Link
              href="/"
              className="rounded-full bg-primary px-6 py-3 font-medium text-white transition hover:bg-primary-dark"
            >
              일정 만들러 가기
            </Link>
            <Link
              href="/"
              className="rounded-full border border-sage-dark px-6 py-3 font-medium text-sage-dark transition hover:bg-sage-light"
            >
              상영작 둘러보기
            </Link>
          </div>
        </div>
      </section>

      {/* 주요 기능 */}
      <section className="mx-auto max-w-6xl px-4 py-16">
        <div className="mb-10 text-center">
          <span className="rounded-full bg-gold-light px-3 py-1 text-xs font-bold tracking-wide text-gold">
            KEY FEATURES
          </span>
          <h2 className="mt-3 text-3xl font-bold text-ink">
            라도 트립이 해드릴 수 있는 것
          </h2>
        </div>
        <div className="grid gap-6 sm:grid-cols-2">
          {features.map((feature) => (
            <div
              key={feature.tag}
              className="rounded-2xl border border-primary-light bg-white p-6 shadow-sm transition hover:shadow-md"
            >
              <span className="rounded-full bg-sage-light px-3 py-1 text-xs font-bold tracking-wide text-sage-dark">
                {feature.tag}
              </span>
              <h3 className="mt-4 text-xl font-bold text-primary">
                {feature.title}
              </h3>
              <p className="mt-2 leading-relaxed text-ink-muted">
                {feature.description}
              </p>
            </div>
          ))}
        </div>
      </section>
    </>
  );
}

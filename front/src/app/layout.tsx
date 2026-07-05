import type { Metadata } from "next";
import { Noto_Sans_KR } from "next/font/google";
import Image from "next/image";
import Link from "next/link";
import "./globals.css";

const notoSansKr = Noto_Sans_KR({
  variable: "--font-noto-sans-kr",
  subsets: ["latin"],
  weight: ["400", "500", "700", "900"],
});

export const metadata: Metadata = {
  title: "라도 트립 - 전주국제영화제 여행 가이드",
  description:
    "전주국제영화제(JIFF) 일정을 중심으로 관광지·맛집·숙소를 엮어 최적의 여행 동선을 추천하는 서비스",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="ko">
      <body className={`${notoSansKr.variable} font-sans antialiased`}>
        <header className="sticky top-0 z-50 border-b border-primary-light bg-white/90 backdrop-blur">
          <div className="mx-auto flex h-16 max-w-6xl items-center justify-between px-4">
            <Link href="/" className="flex items-center gap-2">
              <Image
                src="/logo.png"
                alt="라도 트립 로고"
                width={44}
                height={44}
                className="rounded-full"
              />
              <span className="text-lg font-bold text-primary">
                라도 트립
              </span>
            </Link>
            <nav className="flex items-center gap-6 text-sm font-medium text-ink-muted">
              <Link href="/" className="transition hover:text-primary">
                영화제
              </Link>
              <Link href="/" className="transition hover:text-primary">
                주변 탐색
              </Link>
              <Link href="/" className="transition hover:text-primary">
                축제 연계
              </Link>
              <Link
                href="/"
                className="rounded-full bg-primary px-4 py-2 text-white transition hover:bg-primary-dark"
              >
                내 일정 만들기
              </Link>
            </nav>
          </div>
        </header>
        <main>{children}</main>
        <footer className="border-t border-primary-light bg-white">
          <div className="mx-auto max-w-6xl px-4 py-8 text-center text-sm text-ink-muted">
            <p className="font-medium text-primary">
              RADO TRIP · 라도 트립
            </p>
            <p className="mt-1">
              Jeonju International Film Festival Travel Guide
            </p>
          </div>
        </footer>
      </body>
    </html>
  );
}

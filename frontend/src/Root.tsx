import "./App.css";
import { Link, Outlet, useLoaderData } from "react-router";
import { UserDTO } from "./dto/UserDTO.ts";
import { ModeToggle } from "./components/mode-toggle.tsx";

interface Props {
  user?: UserDTO;
}

export async function loader(): Promise<Props> {
  const userResponse = await fetch("/api/user");
  const user: UserDTO = userResponse.ok ? await userResponse.json() : undefined;

  return {
    user,
  };
}

export function Root() {
  const { user } = useLoaderData<Props>();

  return (
    <>
      <div className="bg-background flex items-center px-4 py-2">
        <div className="grow">
          <Link to={`/`} className="flex max-w-min items-center gap-x-1">
            <img
              alt="Commandinator Logo"
              src="/logo-round.svg"
              className="size-8 rounded-full"
            />
            <span className="grow">Commandinator</span>
          </Link>
        </div>
        <div className="flex items-center gap-x-2">
          <ModeToggle />
          {user ? <User user={user} /> : <LogIn />}
        </div>
      </div>
      <div className="h-auto p-2">
        <Outlet />
      </div>
    </>
  );
}

function User({ user }: { user: UserDTO }) {
  return (
    <div className="flex items-center gap-x-2">
      <span>{user.effectiveName}</span>
      <img
        className="size-8 rounded-full"
        alt={`${user.effectiveName} avatar`}
        src={getAvatarUrl(user)}
      />
    </div>
  );
}

function getAvatarUrl({ id, avatarHash }: UserDTO) {
  if (avatarHash) {
    const extension = avatarHash.startsWith("a_") ? "gif" : "png";
    return `https://cdn.discordapp.com/avatars/${id}/${avatarHash}.${extension}`;
  } else {
    return `https://cdn.discordapp.com/avatars/${(BigInt(id) >> BigInt(22)) % BigInt(6)}.png`;
  }
}

function LogIn() {
  return <button>Log in</button>;
}
